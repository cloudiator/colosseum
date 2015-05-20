package components.installer;

import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;

import play.Logger;

/**
 * Created by Daniel Seybold on 20.05.2015.
 */
public class WindowsInstaller extends AbstractInstaller {
    private final String homeDir;
    private final String javaExe = "jre8.exe";
    private final String sevenZipArchive = "7zip.zip";
    private final String sevenZipDir = "7zip";


    public WindowsInstaller(RemoteConnection remoteConnection, String user) {
        super(remoteConnection);

        this.homeDir = "C:\\Users\\" + user;
    }

    @Override
    public void initSources() {

        //java
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('http://javadl.sun.com/webapps/download/AutoDL?BundleId=107100','"+this.homeDir+"\\"+this.javaExe+"')");
        //7zip
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('http://7-zip.org/a/7za920.zip','"+this.homeDir+"\\"+this.sevenZipArchive+"')");
        //download visor
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('https://omi-dev.e-technik.uni-ulm.de/jenkins/job/cloudiator-visor/lastSuccessfulBuild/artifact/visor-service/target/visor.jar','" + this.homeDir + "\\" + this.visorJar + "')");
        //download kairosDB
        this.sourcesList.add("powershell -command (new-object System.Net.WebClient).DownloadFile('https://github.com/kairosdb/kairosdb/releases/download/v0.9.4/kairosdb-0.9.4-6.tar.gz','"+this.homeDir+"\\"+this.kairosDbArchive+"')");

    }

    @Override
    public void installJava() {

        Logger.debug("Installing Java...");
        this.remoteConnection.executeCommand("powershell -command "+this.homeDir+"jre8.exe /s INSTALLDIR="+this.homeDir+this.javaDir);

        //TODO: verify if java path is set automatically during installation
        //remoteConnection.executeCommand("java -version");

        //TODO: if path vars are not set automatically set them manually
        //remoteConnection.executeCommand("SET PATH=C:\\jre8\\bin;%PATH%");
        //remoteConnection.executeCommand("SET JAVA_HOME=C:\\jre8");
        Logger.debug("Java successfully installed");

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
        this.remoteConnection.writeFile(this.homeDir + "/default.properties", this.buildDefaultVisorConfig(), false);

        String visorJobId = "visor";
        this.remoteConnection.executeCommand("schtasks.exe /create /st 00:00  /sc ONCE /tn "+ visorJobId +" /tr \""+this.homeDir+"\\" + this.visorJar +"-conf default.properties \"");
        this.remoteConnection.executeCommand("schtasks.exe /run /tn " + visorJobId);

        Logger.debug("Visor started successfully!");

    }

    @Override
    public void installKairosDb() {

        Logger.debug("Extract, setup and start KairosDB...");
        //extract kairosdb
        this.remoteConnection.executeCommand("powershell -command " + this.homeDir +"\\"+ this.sevenZipDir +"\\7za.exe e " + this.homeDir + "\\"+ this.kairosDbArchive + " -o" + this.homeDir);
        String kairosDbTar = this.kairosDbArchive.replace(".gz", "");
        this.remoteConnection.executeCommand("powershell -command " + this.homeDir +"\\"+ this.sevenZipDir +"\7za.exe x " + this.homeDir + "\\"+ kairosDbTar +" -o" + this.homeDir);

        //set firewall rule
        this.remoteConnection.executeCommand("powershell -command netsh advfirewall firewall add rule name = 'Open Kairos Port 8080' dir = in action = allow protocol=TCP localport=8080");

        //start kairosdb in backround
        String kairosJobId = "kairosDB";
        this.remoteConnection.executeCommand("schtasks.exe /create /st 00:00  /sc ONCE /tn "+ kairosJobId +" /tr \""+this.homeDir+"\\kairosdb\\bin\\kairosdb.bat run\"");
        this.remoteConnection.executeCommand("schtasks.exe /run /tn " + kairosJobId);
        Logger.debug("KairosDB successfully started!");

    }

    @Override
    public void installLifecycleAgent() {
        Logger.error("InstallLifecycleAgent currently not supported...");
    }

    @Override
    public void installAll() {

        this.initSources();
        this.downloadSources();

        this.installJava();
        this.install7Zip();

        //not used yet
        //this.installKairosDb();

        this.installVisor();

    }
}
