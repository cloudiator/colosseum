package components.log;

import com.google.inject.Inject;
import play.Configuration;

import java.io.File;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 04.10.16.
 */
public class AggregatorLog extends LocalLogFile {

    private static final String AXE_AGGREGATOR_LOG_PATH_CONFIG = "colosseum.log.axeLogPath";
    private final String axeAggregatorLogPath;

    @Inject AggregatorLog(Configuration configuration) {
        checkNotNull(configuration, "configuration is not null");
        axeAggregatorLogPath = configuration.getString(AXE_AGGREGATOR_LOG_PATH_CONFIG);
        checkState(axeAggregatorLogPath != null,
            AXE_AGGREGATOR_LOG_PATH_CONFIG + " is not configured.");
    }

    @Override protected File file() {
        return new File(axeAggregatorLogPath);
    }
}
