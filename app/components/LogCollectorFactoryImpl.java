package components;

import cloud.strategies.RemoteConnectionStrategy;
import com.google.inject.Inject;

/**
 * Created by daniel on 06.06.16.
 */
public class LogCollectorFactoryImpl implements LogCollectorFactory {

    @Inject LogCollectorFactoryImpl(RemoteConnectionStrategy remoteConnectionStrategy) {

    }

    @Override public LogCollector forLogFile(LogFile logFile) {
        //todo find a better way to create those files.
        switch (logFile.logType()) {
            case LOCAL:
                throw new UnsupportedOperationException();
                break;
            case VM:
                break;
            throw new UnsupportedOperationException();
            default:
                throw new AssertionError(String
                    .format("Unsupported logType %s on logFile %s", logFile.logType(), logFile));
        }
    }
}
