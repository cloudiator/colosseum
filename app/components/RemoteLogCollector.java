package components;

import cloud.strategies.RemoteConnectionStrategy;
import com.google.common.base.MoreObjects;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteConnection;
import de.uniulm.omi.cloudiator.sword.api.remote.RemoteException;
import models.VirtualMachine;
import play.Logger;
import util.logging.Loggers;

import java.io.File;
import java.util.Optional;

public class RemoteLogCollector implements LogCollector {

    Logger.ALogger LOGGER = Loggers.of(Loggers.CLOUD_REMOTE);

    private final VirtualMachine virtualMachine;
    private final String remotePath;
    private final RemoteConnectionStrategy remoteConnectionStrategy;

    public RemoteLogCollector(VirtualMachine virtualMachine, String remotePath,
        RemoteConnectionStrategy remoteConnectionStrategy) {
        this.virtualMachine = virtualMachine;
        this.remotePath = remotePath;
        this.remoteConnectionStrategy = remoteConnectionStrategy;
    }

    @Override public Optional<File> get() {
        try (RemoteConnection remoteConnection = this.remoteConnectionStrategy
            .connect(virtualMachine)) {
            return Optional.of(remoteConnection.downloadFile(remotePath));
        } catch (RemoteException e) {
            LOGGER.warn(String.format("Error while collecting %s", this); return Optional.empty();
        }
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("virtualMachine", virtualMachine)
            .add("remotePath", remotePath).add("remoteConnectionStrategy", remoteConnectionStrategy)
            .toString();
    }
}
