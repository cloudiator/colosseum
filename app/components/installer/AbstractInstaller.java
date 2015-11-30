/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package components.installer;

import components.installer.api.InstallApi;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import models.VirtualMachine;
import play.Logger;
import play.Play;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * todo clean up class, do better logging
 */
abstract class AbstractInstaller implements InstallApi {

    protected final RemoteConnection remoteConnection;
    protected final VirtualMachine virtualMachine;


    protected final List<String> sourcesList = new ArrayList<>();

    //parallel download threads
    private static final int NUMBER_OF_DOWNLOAD_THREADS =
        Play.application().configuration().getInt("colosseum.installer.download.threads");

    //KairosDB
    protected static final String KAIROSDB_ARCHIVE = "kairosdb.tar.gz";
    protected static final String KAIRROSDB_DIR = "kairosdb";
    protected static final String KAIROSDB_DOWNLOAD = Play.application().configuration()
        .getString("colosseum.installer.abstract.kairosdb.download");

    //Visor
    protected static final String VISOR_JAR = "visor.jar";
    protected static final String VISOR_DOWNLOAD =
        Play.application().configuration().getString("colosseum.installer.abstract.visor.download");

    //Lance
    protected static final String LANCE_JAR = "lance.jar";
    protected static final String LANCE_DOWNLOAD =
        Play.application().configuration().getString("colosseum.installer.abstract.lance.download");

    //Java
    protected static final String JAVA_DIR = "jre8";


    protected static final String VISOR_PROPERTIES = "default.properties";

    public AbstractInstaller(RemoteConnection remoteConnection, VirtualMachine virtualMachine) {

        checkNotNull(remoteConnection);
        checkNotNull(virtualMachine);
        checkArgument(virtualMachine.publicIpAddress().isPresent(),
            "VirtualMachine has no public ip.");
        checkArgument(virtualMachine.loginName().isPresent(),
            "No login name is present on virtual machine.");

        this.remoteConnection = remoteConnection;
        this.virtualMachine = virtualMachine;

    }

    @Override public void downloadSources() {

        Logger.debug("Start downloading sources...");
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_DOWNLOAD_THREADS);

        List<Callable<Integer>> tasks = new ArrayList<>();

        for (final String downloadCommand : this.sourcesList) {

            Callable<Integer> downloadTask =
                new DownloadTask(this.remoteConnection, downloadCommand);
            tasks.add(downloadTask);
        }
        try {
            List<Future<Integer>> results = executorService.invokeAll(tasks);

            for (Future<Integer> exitCode : results) {
                if (exitCode.get() != 0) {
                    throw new RuntimeException("Downloading of one or more sources failed!");
                }
            }
            Logger.debug("All sources downloaded successfully!");
            executorService.shutdown();
        } catch (InterruptedException e) {
            Logger.error("Installer: Interrupted Exception while downloading sources!", e);
        } catch (ExecutionException e) {
            Logger.error("Installer: Execution Exception while downloading sources!", e);
        }

    }

    protected String buildDefaultVisorConfig() {

        //KairosServer depends if visor should connect to vm local kairos or to honme domain kairos
        //get home domain ip
        //TODO: get public ip if running Cloudiator on a VM in e.g. Openstack
        /*
        InetAddress inetAddress = null;
        try {
            inetAddress=InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        }
        String homeDomainIp = inetAddress.getHostAddress();
        */

        String config = "executionThreads = " + Play.application().configuration()
            .getString("colosseum.installer.abstract.visor.config.executionThreads") + "\n" +
            "reportingInterval = " + Play.application().configuration()
            .getString("colosseum.installer.abstract.visor.config.reportingInterval") + "\n" +
            "telnetPort = " + Play.application().configuration()
            .getString("colosseum.installer.abstract.visor.config.telnetPort") + "\n" +
            "restHost = " + Play.application().configuration()
            .getString("colosseum.installer.abstract.visor.config.restHost") + "\n" +
            "restPort = " + Play.application().configuration()
            .getString("colosseum.installer.abstract.visor.config.restPort") + "\n" +
            "kairosServer = " + Play.application().configuration()
            .getString("colosseum.installer.abstract.visor.config.kairosServer") + "\n" +
            "kairosPort = " + Play.application().configuration()
            .getString("colosseum.installer.abstract.visor.config.kairosPort") + "\n" +
            "reportingModule = " + Play.application().configuration()
            .getString("colosseum.installer.abstract.visor.config.reportingModule");


        return config;

    }

    @Override public void close() {
        Logger.info("Installation of all tools finished, closing remote connection!");
        this.remoteConnection.close();
    }
}

