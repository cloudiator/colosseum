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
 * Created by Daniel Seybold on 20.05.2015.
 */
public class WindowsInstaller extends AbstractInstaller {
    private final String homeDir;
    private static final String JAVA_EXE = "jre8.exe";
    private static final String SEVEN_ZIP_ARCHIVE = "7za920.zip";
    private static final String SEVEN_ZIP_DIR = "7zip";
    private static final String SEVEN_ZIP_EXE = "7za.exe";
    private static final String VISOR_BAT = "startVisor.bat";
    private static final String KAIROSDB_BAT = "startKairos.bat";
    private static final String JAVA_DOWNLOAD = Play.application().configuration().getString("colosseum.installer.windows.java.download");
    private static final String SEVEN_ZIP_DOWNLOAD = Play.application().configuration().getString("colosseum.installer.windows.7zip.download");



    public WindowsInstaller(RemoteConnection remoteConnection, VirtualMachine virtualMachine, Tenant tenant) {
        super(remoteConnection, virtualMachine, tenant);

        this.homeDir = "C:\\Users\\" + virtualMachine.getLoginName();
    }

    @Override
    public void initSources() {

        //java
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('" + WindowsInstaller.JAVA_DOWNLOAD + "','"+this.homeDir+"\\"+WindowsInstaller.JAVA_EXE+"')");
        //7zip
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('" + WindowsInstaller.SEVEN_ZIP_DOWNLOAD + "','"+this.homeDir+"\\"+WindowsInstaller.SEVEN_ZIP_ARCHIVE +"')");
        //download visor
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('" + WindowsInstaller.VISOR_DOWNLOAD + "','" + this.homeDir + "\\" + WindowsInstaller.VISOR_JAR + "')");
        //download kairosDB
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('" + WindowsInstaller.KAIROSDB_DOWNLOAD + "','"+this.homeDir+"\\"+WindowsInstaller.KAIROSDB_ARCHIVE +"')");
        //lance
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('" + WindowsInstaller.LANCE_DOWNLOAD + "','"+this.homeDir+"\\"+WindowsInstaller.LANCE_JAR +"')");


    }

    @Override
    public void installJava() {

        Logger.debug("Installing Java...");
        this.remoteConnection.executeCommand("powershell -command " + this.homeDir + "\\jre8.exe /s INSTALLDIR=" + this.homeDir + "\\" + WindowsInstaller.JAVA_DIR);

        //Set JAVA envirnonment vars, use SETX for setting the vars for all future session use /m for machine scope
        remoteConnection.executeCommand("SETX PATH %PATH%;" + this.homeDir + "\\" + WindowsInstaller.JAVA_DIR + "\\bin /m");
        remoteConnection.executeCommand("SETX JAVA_HOME " + this.homeDir + "\\" + WindowsInstaller.JAVA_DIR + " /m");

        Logger.debug("Java successfully installed!");


    }

    private void install7Zip(){
        Logger.debug("Unzipping 7zip...");
        this.remoteConnection.executeCommand("powershell -command & { Add-Type -A 'System.IO.Compression.FileSystem'; [IO.Compression.ZipFile]::ExtractToDirectory('" + this.homeDir + "\\" + WindowsInstaller.SEVEN_ZIP_ARCHIVE +"', '" + this.homeDir + "\\" + WindowsInstaller.SEVEN_ZIP_DIR +  "'); }");
        Logger.debug("7zip successfully unzipped!");
    }

    @Override
    public void installVisor() {

        Logger.debug("Setting up and starting Visor");

        //create properties file
        this.remoteConnection.writeFile(this.homeDir + "\\" + WindowsInstaller.VISOR_PROPERTIES, this.buildDefaultVisorConfig(), false);

        //id of the visor schtasks
        String visorJobId = "visor";

        //create a .bat file to start visor, because it is not possible to pass schtasks paramters using overthere
        String startCommand = "java -jar " + this.homeDir + "\\" + WindowsInstaller.VISOR_JAR + " -conf " + this.homeDir + "\\" + WindowsInstaller.VISOR_PROPERTIES;
        this.remoteConnection.writeFile(this.homeDir + "\\" + WindowsInstaller.VISOR_BAT, startCommand, false );

        //set firewall rules
        this.remoteConnection.executeCommand("powershell -command netsh advfirewall firewall add rule name = 'Open Visor Rest Port 9001' dir = in action = allow protocol=TCP localport=9001");
        this.remoteConnection.executeCommand("powershell -command netsh advfirewall firewall add rule name = 'Open Visor Rest Port 9001' dir = in action = allow protocol=TCP localport=9001");



        //create schtaks
        this.remoteConnection.executeCommand("schtasks.exe /create /st 00:00  /sc ONCE /tn " + visorJobId + " /tr \"" + this.homeDir + "\\" + WindowsInstaller.VISOR_BAT + "\"");
        this.waitForSchtaskCreation();
        //run schtask
        this.remoteConnection.executeCommand("schtasks.exe /run /tn " + visorJobId );

        Logger.debug("Visor started successfully!");

    }

    @Override
    public void installKairosDb() {

        Logger.debug("Extract, setup and start KairosDB...");
        //extract kairosdb
        this.remoteConnection.executeCommand("powershell -command " + this.homeDir + "\\" + WindowsInstaller.SEVEN_ZIP_DIR + "\\" + WindowsInstaller.SEVEN_ZIP_EXE + " e " + this.homeDir + "\\" + WindowsInstaller.KAIROSDB_ARCHIVE + " -o" + this.homeDir);
        String kairosDbTar = WindowsInstaller.KAIROSDB_ARCHIVE.replace(".gz", "");
        this.remoteConnection.executeCommand("powershell -command " + this.homeDir + "\\" + WindowsInstaller.SEVEN_ZIP_DIR + "\\" +WindowsInstaller.SEVEN_ZIP_EXE + " x " + this.homeDir + "\\" + kairosDbTar + " -o" + this.homeDir);

        //set firewall rule
        this.remoteConnection.executeCommand("powershell -command netsh advfirewall firewall add rule name = 'Open Kairos Port 8080' dir = in action = allow protocol=TCP localport=8080");

        //create a .bat file to start kairosDB, because it is not possible to pass schtasks paramters using overthere
        String startCommand = this.homeDir + "\\" + WindowsInstaller.KAIRROSDB_DIR + "\\bin\\kairosdb.bat run";
        this.remoteConnection.writeFile(this.homeDir + "\\" + WindowsInstaller.KAIROSDB_BAT, startCommand, false );

        //start kairosdb in backround
        String kairosJobId = "kairosDB";
        this.remoteConnection.executeCommand("schtasks.exe /create /st 00:00  /sc ONCE /tn " + kairosJobId + " /tr \"" + this.homeDir + "\\" + WindowsInstaller.KAIROSDB_BAT + "\"");
        this.waitForSchtaskCreation();
        this.remoteConnection.executeCommand("schtasks.exe /run /tn " + kairosJobId);
        Logger.debug("KairosDB successfully started!");

    }

    @Override
    public void installLance() {
        Logger.error("Setting up Lance...");

        Logger.error("Opening Firewall ports for Lance...");
        this.remoteConnection.executeCommand("powershell -command netsh advfirewall firewall add rule name = 'Open Visor Rest Port 9001' dir = in action = allow protocol=TCP localport=9001");
        this.remoteConnection.executeCommand("powershell -command netsh advfirewall firewall add rule name = 'Open Visor Rest Port 9001' dir = in action = allow protocol=TCP localport=9001");


    }

    @Override
    public void installAll() {

        Logger.debug("Starting installation of all tools on WINDOWS...");

        this.initSources();
        this.downloadSources();

        this.installJava();

        this.installLance();

        this.install7Zip();
        this.installKairosDb();

        this.installVisor();

        this.finishInstallation();


    }

    private void waitForSchtaskCreation(){
        //Sleep 5 seconds to make sure the schtask creation is finished
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Logger.error("Error while waiting for schtask to be created!", e);
            e.printStackTrace();
        }
    }
}
