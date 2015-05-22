package components.installer;

import components.installer.api.InstallApi;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import play.Logger;

/**
 * Created by Daniel Seybold on 19.05.2015.
 */
abstract class AbstractInstaller implements InstallApi {

    protected  final  RemoteConnection remoteConnection;
    protected final List<String> sourcesList = new ArrayList<>();

    protected final String kairosDbArchive = "kairosdb.tar.gz";
    protected final String visorJar = "visor.jar";

    protected final String dockerInstall = "docker_install.sh ";

    protected final String javaDir = "jre8";
    protected final String kairosDbDir = "kairosdb";

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


        //TODO: get public ip if running Cloudiator on a VM in e.g. Openstack
        InetAddress inetAddress = null;
        try {
            inetAddress=InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
        }
        String localIp = inetAddress.getHostName();

        String config = "executionThreads = 20\n" +
                "reportingInterval = 10\n" +
                "telnetPort = 9001\n" +
                "restHost = http://0.0.0.0\n" +
                "restPort = 9002\n" +
                "kairosServer = "+localIp+"\n" +
                "kairosPort = 8080\n" +
                "reportingModule = de.uniulm.omi.cloudiator.visor.reporting.cli.CommandLineReportingModule ";


        return config;

    }
}

