package components.log;

import cloud.strategies.RemoteConnectionStrategy;
import com.google.inject.Inject;
import models.VirtualMachine;
import play.Configuration;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 04.10.16.
 */
public class VisorLogFile extends VMLogFile {

    private final String visorLogName;

    @Inject VisorLogFile(Configuration configuration,
        RemoteConnectionStrategy.RemoteConnectionStrategyFactory remoteConnectionStrategyFactory) {
        super(remoteConnectionStrategyFactory);
        checkNotNull(configuration, "configuration is null");
        visorLogName = configuration.getString("colosseum.log.visorFileName");
        checkState(visorLogName != null, "colosseum.log.visorFileName not configured");
    }

    @Override protected String location(VirtualMachine virtualMachine) {
        String homeDir =
            virtualMachine.operatingSystem().operatingSystemFamily().operatingSystemType()
                .homeDirFunction().apply(virtualMachine.loginName());
        return homeDir + "/logs/" + visorLogName;
    }
}
