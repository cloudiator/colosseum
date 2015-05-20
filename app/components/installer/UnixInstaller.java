package components.installer;

import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;

import play.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by Daniel Seybold on 19.05.2015.
 */
public class UnixInstaller extends AbstractInstaller {
    private final String homeDir;
    private final String javaArchive = "jre8.tar.gz";

    public UnixInstaller(RemoteConnection remoteConnection, String user) {
        super(remoteConnection);

        this.homeDir = "/home/" + user;
    }

    @Override
    public void initSources() {

        //java
        this.sourcesList.add("wget http://javadl.sun.com/webapps/download/AutoDL?BundleId=106240 -O " + this.javaArchive);
        //docker
        this.sourcesList.add("wget https://get.docker.com/ubuntu/ -O " + this.dockerInstall);
        //kairosDB
        this.sourcesList.add("wget https://github.com/kairosdb/kairosdb/releases/download/v0.9.4/kairosdb-0.9.4-6.tar.gz -O " + this.kairosDbArchive);
        //visor
        this.sourcesList.add("wget https://omi-dev.e-technik.uni-ulm.de/jenkins/job/cloudiator-visor/lastSuccessfulBuild/artifact/visor-service/target/visor.jar -O " + this.visorJar);
    }



    @Override
    public void installJava() {

        Logger.debug("Starting Java installation...");
        //create directory
        this.remoteConnection.executeCommand("mkdir " + this.javaDir);
        //extract java
        this.remoteConnection.executeCommand("tar zxvf "+this.javaArchive+" -C "+this.javaDir+" --strip-components=1");
        //set symbolic link
        this.remoteConnection.executeCommand("sudo ln -s "+ this.homeDir + "/"+this.javaDir+"/bin/java /usr/bin/java");
        Logger.debug("Java was successfully installed!");
    }

    @Override
    public void installVisor() {

        Logger.debug("setting up Visor...");

        //create properties file
        this.remoteConnection.writeFile(this.homeDir + "/default.properties",this.buildDefaultVisorConfig(), false);

        //start visor
        this.remoteConnection.executeCommand("java -jar "+this.visorJar+" -conf default.properties &> /dev/null &");
        Logger.debug("Visor started successfully!");
    }

    @Override
    public void installKairosDb() {

        Logger.debug("Installing and starting KairosDB...");
        this.remoteConnection.executeCommand("mkdir " + this.kairosDbDir);

        this.remoteConnection.executeCommand("tar  zxvf "+this.kairosDbArchive+" -C "+ this.kairosDbDir +" --strip-components=1");

        this.remoteConnection.executeCommand(" sudo "+this.kairosDbDir+"/bin/kairosdb.sh start");
        Logger.debug("KairosDB started successfully!");
    }

    @Override
    public void installLifecycleAgent() {

        //install docker
        Logger.debug("Installing and starting LifecycleAgent:Docker...");
        this.remoteConnection.executeCommand("sudo chmod +x " + this.dockerInstall);
        this.remoteConnection.executeCommand("sudo ./" + this.dockerInstall);
        this.remoteConnection.executeCommand("sudo service docker restart");
        Logger.debug("LifecycleAgent:Docker installed and started successfully!");
    }

    @Override
    public void installAll() {

        this.initSources();
        this.downloadSources();

        this.installJava();

        this.installLifecycleAgent();

        //not yet needed
        //this.installKairosDb();

        this.installVisor();


    }
}

