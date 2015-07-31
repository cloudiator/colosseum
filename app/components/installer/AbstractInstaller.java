package components.installer;

import components.installer.api.InstallApi;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import play.Logger;
import play.Play;

/**
 * Created by Daniel Seybold on 19.05.2015.
 */
abstract class AbstractInstaller implements InstallApi {

    protected  final  RemoteConnection remoteConnection;
    protected final List<String> sourcesList = new ArrayList<>();

    //KairosDB
    protected final String kairosDbArchive = "kairosdb.tar.gz";
    protected final String kairosDbDir = "kairosdb";
    protected final String kairosDbDownload = Play.application().configuration().getString("colosseum.installer.abstract.kairosdb.download");

    //Visor
    protected final String visorJar = "visor.jar";
    protected final String visorDownload = Play.application().configuration().getString("colosseum.installer.abstract.visor.download");

    protected final String javaDir = "jre8";


    protected final String visorProperties = "default.properties";

    public AbstractInstaller(RemoteConnection remoteConnection){

        this.remoteConnection = remoteConnection;
    }

    @Override
    public void downloadSources(){

        Logger.debug("Start downloading sources...");
        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Callable<Integer>> tasks = new ArrayList<>();

        for (final String downloadCommand : this.sourcesList) {

            Callable<Integer> downloadTask = new DownloadTask(this.remoteConnection, downloadCommand);
            tasks.add(downloadTask);
        }
        try {
            List<Future<Integer>> results = executorService.invokeAll(tasks);

            for(Future<Integer> exitCode : results){
                if(exitCode.get() != 0){
                    throw new RuntimeException("Downloading of one or more sources failed!");
                }
            }
            Logger.debug("All sources downloaded successfully!");
        } catch (InterruptedException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    protected String buildDefaultVisorConfig(){

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

        String config = "executionThreads = " + Play.application().configuration().getString("colosseum.installer.abstract.visor.config.executionThreads") +"\n" +
                "reportingInterval = " + Play.application().configuration().getString("colosseum.installer.abstract.visor.config.reportingInterval") +"\n" +
                "telnetPort = " + Play.application().configuration().getString("colosseum.installer.abstract.visor.config.telnetPort") +"\n" +
                "restHost = " + Play.application().configuration().getString("colosseum.installer.abstract.visor.config.restHost") +"\n" +
                "restPort = " + Play.application().configuration().getString("colosseum.installer.abstract.visor.config.restPort") +"\n" +
                "kairosServer = " + Play.application().configuration().getString("colosseum.installer.abstract.visor.config.kairosServer") +"\n" +
                "kairosPort = " + Play.application().configuration().getString("colosseum.installer.abstract.visor.config.kairosPort") +"\n" +
                "reportingModule = " + Play.application().configuration().getString("colosseum.installer.abstract.visor.config.reportingModule");


        return config;

    }

    public void finishInstallation(){

        Logger.debug("Finished installation of all tools, closing remote connection.");
        this.remoteConnection.close();

    }
}

