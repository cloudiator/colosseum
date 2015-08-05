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

import play.Logger;
import play.Play;

/**
 * Created by Daniel Seybold on 20.05.2015.
 */
public class WindowsInstaller extends AbstractInstaller {
    private final String homeDir;
    private final String javaExe = "jre8.exe";
    private final String sevenZipArchive = "7zip.zip";
    private final String sevenZipDir = "7zip";
    private final String visorBat = "startVisor.bat";
    private final String javaDonwload = Play.application().configuration().getString("colosseum.installer.windows.java.download");
    private final String zip7Donwload = Play.application().configuration().getString("colosseum.installer.windows.java.download");


    public WindowsInstaller(RemoteConnection remoteConnection, String user) {
        super(remoteConnection);

        this.homeDir = "C:\\Users\\" + user;
    }

    @Override
    public void initSources() {

        //java
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('" + this.javaDonwload + "','"+this.homeDir+"\\"+this.javaExe+"')");
        //7zip
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('http://7-zip.org/a/7za920.zip','"+this.homeDir+"\\"+this.sevenZipArchive+"')");
        //download visor
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('" + this.VISOR_DOWNLOAD + "','" + this.homeDir + "\\" + this.VISOR_JAR + "')");
        //download kairosDB
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('" + this.KAIROSDB_DOWNLOAD + "','"+this.homeDir+"\\"+this.KAIROSDB_ARCHIVE +"')");

    }

    @Override
    public void installJava() {

        Logger.debug("Installing Java...");
        this.remoteConnection.executeCommand("powershell -command " + this.homeDir + "\\jre8.exe /s INSTALLDIR=" + this.homeDir + "\\" + this.JAVA_DIR);

        //Set JAVA envirnonment vars
        remoteConnection.executeCommand("SET PATH="+this.homeDir+"\\"+WindowsInstaller.JAVA_DIR + "\\bin;%PATH%");
        remoteConnection.executeCommand("SET JAVA_HOME=" + this.homeDir + "\\" + WindowsInstaller.JAVA_DIR);


    }

    private void install7Zip(){
        Logger.debug("Unzipping 7zip...");
        this.remoteConnection.executeCommand("powershell -command & { Add-Type -A 'System.IO.Compression.FileSystem'; [IO.Compression.ZipFile]::ExtractToDirectory('" + this.homeDir + "7zip.zip', '" + this.homeDir + "\\"+ this.sevenZipDir+ "'); }");
        Logger.debug("7zip successfully unzipped!");
    }

    @Override
    public void installVisor() {

        Logger.debug("Setting up and starting Visor");

        //create properties file
        this.remoteConnection.writeFile(this.homeDir + "\\" + this.visorProperties, this.buildDefaultVisorConfig(), false);

        //id of the visor schtasks
        String visorJobId = "visor";

        //create a .bat file to start visor, because it is not possible to pass schtasks paramters using overthere
        String startCommand = "java -jar " + this.homeDir + "\\" + this.VISOR_JAR + " -conf " + this.homeDir + "\\" + this.visorProperties;
        this.remoteConnection.writeFile(this.homeDir + "\\" + this.visorBat, startCommand, false );

        //TODO: open WindowsFirewall Ports if Rest/Telnet ports need to be remote accessible

        //create schtaks
        this.remoteConnection.executeCommand("schtasks.exe /create /st 00:00  /sc ONCE /tn " + visorJobId + " /tr \"java -jar " + this.homeDir + "\\" + this.visorBat + "\"");
        //run schtask
        this.remoteConnection.executeCommand("schtasks.exe /run /tn " + visorJobId );

        Logger.debug("Visor started successfully!");

    }

    @Override
    public void installKairosDb() {

        Logger.debug("Extract, setup and start KairosDB...");
        //extract kairosdb
        this.remoteConnection.executeCommand("powershell -command " + this.homeDir +"\\"+ this.sevenZipDir +"\\7za.exe e " + this.homeDir + "\\"+ this.KAIROSDB_ARCHIVE + " -o" + this.homeDir);
        String kairosDbTar = this.KAIROSDB_ARCHIVE.replace(".gz", "");
        this.remoteConnection.executeCommand("powershell -command " + this.homeDir + "\\" + this.sevenZipDir + "\7za.exe x " + this.homeDir + "\\" + kairosDbTar + " -o" + this.homeDir);

        //set firewall rule
        this.remoteConnection.executeCommand("powershell -command netsh advfirewall firewall add rule name = 'Open Kairos Port 8080' dir = in action = allow protocol=TCP localport=8080");

        //start kairosdb in backround
        String kairosJobId = "kairosDB";
        this.remoteConnection.executeCommand("schtasks.exe /create /st 00:00  /sc ONCE /tn "+ kairosJobId +" /tr \""+this.homeDir+"\\kairosdb\\bin\\kairosdb.bat run\"");
        this.remoteConnection.executeCommand("schtasks.exe /run /tn " + kairosJobId);
        Logger.debug("KairosDB successfully started!");

    }

    @Override
    public void installLance() {
        Logger.error("InstallLifecycleAgent currently not supported...");
    }
    
    @Override
    public void installAll() {

        Logger.debug("Starting installation of all tools on WINDOWS...");

        this.initSources();
        this.downloadSources();

        this.installJava();

        this.install7Zip();
        this.installKairosDb();

        this.installVisor();

        this.finishInstallation();


    }
}
