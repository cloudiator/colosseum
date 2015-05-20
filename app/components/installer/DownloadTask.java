package components.installer;

import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;

import java.util.concurrent.Callable;

/**
 * Created by Daniel Seybold on 19.05.2015.
 */
public class DownloadTask implements Callable<Integer> {

    private final RemoteConnection remoteConnection;
    private final String command;

    public DownloadTask(RemoteConnection remoteConnection, String command){
        this.remoteConnection = remoteConnection;
        this.command = command;

    }

    @Override
    public Integer call() throws Exception {
        return this.remoteConnection.executeCommand(this.command);
    }
}
