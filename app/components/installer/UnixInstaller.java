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

import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import models.Tenant;
import models.VirtualMachine;
import play.Logger;
import play.Play;
import util.logging.Loggers;


/**
 * todo clean up class, do better logging
 */
public class UnixInstaller extends AbstractInstaller {

    private static final Logger.ALogger LOGGER = Loggers.of(Loggers.INSTALLATION);
    private static final String DOCKER_FIX_MTU_INSTALL = "docker_fix_mtu.sh";
    protected final String homeDir;
    private final String JAVA_BINARY;
    private static final String JAVA_ARCHIVE = "jre8.tar.gz";
    private static final String JAVA_DOWNLOAD =
        Play.application().configuration().getString("colosseum.installer.linux.java.download");
    private static final String DOCKER_RETRY_DOWNLOAD = Play.application().configuration()
        .getString("colosseum.installer.linux.lance.docker_retry.download");
    private static final String DOCKER_FIX_MTU_DOWNLOAD = Play.application().configuration()
        .getString("colosseum.installer.linux.lance.docker.mtu.download");
    private static final String DOCKER_RETRY_INSTALL = "docker_retry.sh";
    private static final boolean KAIROS_REQUIRED = Play.application().configuration()
        .getBoolean("colosseum.installer.linux.kairosdb.install.flag");
    private static final boolean DOCKER_REQUIRED = Play.application().configuration()
        .getBoolean("colosseum.installer.linux.lance.docker.install.flag");
    private final Tenant tenant;

    public UnixInstaller(RemoteConnection remoteConnection, VirtualMachine virtualMachine,
        Tenant tenant) {
        super(remoteConnection, virtualMachine);
        this.tenant = tenant;

        //TODO: maybe use a common installation directory, e.g. /opt/cloudiator
        if (virtualMachine.loginName().get().equals("root")) {
            this.homeDir = "/root";
        } else {
            this.homeDir = "/home/" + virtualMachine.loginName().get();
        }

        this.JAVA_BINARY = this.homeDir + "/" + UnixInstaller.JAVA_DIR + "/bin/java";
    }

    @Override public void initSources() {

        //java
        this.sourcesList
            .add("wget " + UnixInstaller.JAVA_DOWNLOAD + "  -O " + UnixInstaller.JAVA_ARCHIVE);
        //lance
        this.sourcesList
            .add("wget " + UnixInstaller.LANCE_DOWNLOAD + "  -O " + UnixInstaller.LANCE_JAR);

        if (DOCKER_REQUIRED) {
            //docker
            this.sourcesList.add("wget " + UnixInstaller.DOCKER_RETRY_DOWNLOAD + "  -O "
                + UnixInstaller.DOCKER_RETRY_INSTALL);
            this.sourcesList.add("wget " + UnixInstaller.DOCKER_FIX_MTU_DOWNLOAD + "  -O "
                + UnixInstaller.DOCKER_FIX_MTU_INSTALL);
        }

        if (KAIROS_REQUIRED) {
            //kairosDB
            this.sourcesList.
                add("wget " + UnixInstaller.KAIROSDB_DOWNLOAD + "  -O "
                    + UnixInstaller.KAIROSDB_ARCHIVE);
        }
        //visor
        this.sourcesList
            .add("wget " + UnixInstaller.VISOR_DOWNLOAD + "  -O " + UnixInstaller.VISOR_JAR);

    }



    @Override public void installJava() throws RemoteException {

        LOGGER.debug(String.format("Starting Java installation on vm %s", virtualMachine));
        //create directory
        this.remoteConnection.executeCommand("mkdir " + UnixInstaller.JAVA_DIR);
        //extract java
        this.remoteConnection.executeCommand(
            "tar zxvf " + UnixInstaller.JAVA_ARCHIVE + " -C " + UnixInstaller.JAVA_DIR
                + " --strip-components=1");
        // do not set symbolic link or PATH as there might be other Java versions on the VM

        LOGGER.debug(String.format("Java was successfully installed on vm %s", virtualMachine));
    }

    @Override public void installVisor() throws RemoteException {

        LOGGER.debug(String.format("Setting up Visor on vm %s", virtualMachine));
        //create properties file
        this.remoteConnection.writeFile(this.homeDir + "/" + UnixInstaller.VISOR_PROPERTIES,
            this.buildDefaultVisorConfig(), false);

        //start visor
        this.remoteConnection.executeCommand(
            "sudo nohup bash -c '"+ this.JAVA_BINARY +" -jar " + UnixInstaller.VISOR_JAR + " -conf "
                + UnixInstaller.VISOR_PROPERTIES + " &> /dev/null &'");
        LOGGER.debug(String.format("Visor started successfully on vm %s", virtualMachine));
    }

    @Override public void installKairosDb() throws RemoteException {

        if (KAIROS_REQUIRED) {

            LOGGER
                .debug(String.format("Installing and starting KairosDB on vm %s", virtualMachine));
            this.remoteConnection.executeCommand("mkdir " + UnixInstaller.KAIRROSDB_DIR);

            this.remoteConnection.executeCommand(
                "tar  zxvf " + UnixInstaller.KAIROSDB_ARCHIVE + " -C " + UnixInstaller.KAIRROSDB_DIR
                    + " --strip-components=1");

            this.remoteConnection.executeCommand(
                " sudo su -c \"(export PATH=\""+ this.homeDir +"/jre8/bin/:\"$PATH;nohup " + UnixInstaller.KAIRROSDB_DIR + "/bin/kairosdb.sh start)\"");

            LOGGER.debug(String.format("KairosDB started successfully on vm %s", virtualMachine));
        }
    }

    @Override public void installLance() throws RemoteException {

        if (DOCKER_REQUIRED) {
            LOGGER.debug(
                String.format("Installing and starting Lance: Docker on vm %s", virtualMachine));

            this.remoteConnection
                .executeCommand("sudo chmod +x " + UnixInstaller.DOCKER_RETRY_INSTALL);
            // Install docker via the retry script:
            this.remoteConnection.executeCommand(
                "sudo nohup ./" + UnixInstaller.DOCKER_RETRY_INSTALL
                    + " > docker_retry_install.out 2>&1");
            this.remoteConnection
                .executeCommand("sudo chmod +x " + UnixInstaller.DOCKER_FIX_MTU_INSTALL);
            this.remoteConnection.executeCommand(
                "sudo nohup ./" + UnixInstaller.DOCKER_FIX_MTU_INSTALL
                    + " > docker_mtu_fix.out 2>&1");
            this.remoteConnection.executeCommand(
                "sudo nohup bash -c 'service docker restart' > docker_start.out 2>&1 ");

        }
        LOGGER.debug(String.format("Installing and starting Lance on vm %s", virtualMachine));

        //start Lance
        this.remoteConnection.executeCommand("nohup bash -c '"+ this.JAVA_BINARY + " " +
            " -Dhost.ip.public=" + this.virtualMachine.publicIpAddress().get().getIp() +
            " -Dhost.ip.private=" + this.virtualMachine.privateIpAddress(true).get().getIp() +
            " -Djava.rmi.server.hostname=" + this.virtualMachine.publicIpAddress().get().getIp() +
            " -Dhost.vm.id=" + this.virtualMachine.getUuid() +
            " -Dhost.vm.cloud.tenant.id=" + this.tenant.getUuid() +
            " -Dhost.vm.cloud.id=" + this.virtualMachine.cloud().getUuid() +
            " -jar " + UnixInstaller.LANCE_JAR +
            " > lance.out 2>&1 &' > lance.out 2>&1");

        LOGGER.debug(
            String.format("Lance installed and started successfully on vm %s", virtualMachine));
    }

    @Override public void installAll() throws RemoteException {

        LOGGER.debug(
            String.format("Starting installation of all tools on UNIX on vm %s", virtualMachine));

        this.initSources();
        this.downloadSources();

        this.installJava();

        this.installLance();

        this.installKairosDb();

        this.installVisor();
    }
}

