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

import models.Tenant;
import models.VirtualMachine;
import play.Logger;
import play.Play;


/**
 * Created by Daniel Seybold on 19.05.2015.
 */
public class UnixInstaller extends AbstractInstaller {

    protected final String homeDir;
    private static final String JAVA_ARCHIVE = "jre8.tar.gz";
    private static final String JAVA_DOWNLOAD = Play.application().configuration().getString("colosseum.installer.linux.java.download");
    private static final String DOCKER_DOWNLOAD = Play.application().configuration().getString("colosseum.installer.linux.lance.docker.download");
    private static final String DOCKER_INSTALL = "docker_install.sh";

    public UnixInstaller(RemoteConnection remoteConnection, VirtualMachine virtualMachine, Tenant tenant, String user) {
        super(remoteConnection, virtualMachine, tenant);

        this.homeDir = "/home/" + user;
    }

    @Override
    public void initSources() {

        //java
        this.sourcesList.add("wget " + UnixInstaller.JAVA_DOWNLOAD + " -O " + UnixInstaller.JAVA_ARCHIVE);
        //lance
        this.sourcesList.add("wget " + UnixInstaller.LANCE_DOWNLOAD + " -O " + UnixInstaller.LANCE_JAR);
        //docker
        this.sourcesList.add("wget " + UnixInstaller.DOCKER_DOWNLOAD + " -O " + UnixInstaller.DOCKER_INSTALL);
        //kairosDB
        this.sourcesList.add("wget " + UnixInstaller.KAIROSDB_DOWNLOAD + " -O " + UnixInstaller.KAIROSDB_ARCHIVE);
        //visor
        this.sourcesList.add("wget " + UnixInstaller.VISOR_DOWNLOAD + " -O " + UnixInstaller.VISOR_JAR);

    }



    @Override
    public void installJava() {

        Logger.debug("Starting Java installation...");
        //create directory
        this.remoteConnection.executeCommand("mkdir " + UnixInstaller.JAVA_DIR);
        //extract java
        this.remoteConnection.executeCommand("tar zxvf "+UnixInstaller.JAVA_ARCHIVE +" -C "+UnixInstaller.JAVA_DIR +" --strip-components=1");
        //set symbolic link
        this.remoteConnection.executeCommand("sudo ln -s "+ this.homeDir + "/"+UnixInstaller.JAVA_DIR +"/bin/java /usr/bin/java");
        Logger.debug("Java was successfully installed!");
    }

    @Override
    public void installVisor() {

        Logger.debug("setting up Visor...");

        //create properties file
        this.remoteConnection.writeFile(this.homeDir + "/" + UnixInstaller.VISOR_PROPERTIES, this.buildDefaultVisorConfig(), false);

        //start visor
        this.remoteConnection.executeCommand("java -jar "+UnixInstaller.VISOR_JAR +" -conf " + UnixInstaller.VISOR_PROPERTIES + " &> /dev/null &");
        Logger.debug("Visor started successfully!");
    }

    @Override
    public void installKairosDb() {

        Logger.debug("Installing and starting KairosDB...");
        this.remoteConnection.executeCommand("mkdir " + UnixInstaller.KAIRROSDB_DIR);

        this.remoteConnection.executeCommand("tar  zxvf "+UnixInstaller.KAIROSDB_ARCHIVE +" -C "+ UnixInstaller.KAIRROSDB_DIR +" --strip-components=1");

        this.remoteConnection.executeCommand(" sudo "+UnixInstaller.KAIRROSDB_DIR +"/bin/kairosdb.sh start");
        Logger.debug("KairosDB started successfully!");
    }

    @Override
    public void installLance() {

        //install Lance
        Logger.debug("Installing and starting LanceLifecycleAgent:Docker...");

        //install docker
        Logger.debug("Installing and starting Docker...");
        this.remoteConnection.executeCommand("sudo chmod +x " + UnixInstaller.DOCKER_INSTALL);
        this.remoteConnection.executeCommand("sudo ./" + UnixInstaller.DOCKER_INSTALL);
        this.remoteConnection.executeCommand("sudo service docker restart");
        //start Lance

        Logger.debug("Installtion of Docker finished, setting up Lance...");
        this.remoteConnection.executeCommand("java " +
                " -Dhost.ip.public=" + this.virtualMachine.publicIpAddress() +
                " -Dhost.ip.private=" + this.virtualMachine.privateIpAddress(true) +
                " -Djava.rmi.server.hostname=" + this.virtualMachine.publicIpAddress() +
                " -DVM_ID=" + this.virtualMachine.getUuid() +
                " -DTENANT_ID=" + this.tenant.getUuid() +
                " -Dhost.vm.cloud.id=" + this.virtualMachine.cloud().getUuid() +
                " de.uniulm.omi.cloudiator.lance.lca.LifecycleAgentBooter " +
                " -jar " + UnixInstaller.LANCE_JAR +
                " &> /dev/null &");

        Logger.debug("Lance installed and started successfully!");
    }

    @Override
    public void installAll() {

        Logger.debug("Starting installation of all tools on UNIX...");

        this.initSources();
        this.downloadSources();

        this.installJava();

        this.installLance();

        this.installKairosDb();

        this.installVisor();

        this.finishInstallation();


    }
}

