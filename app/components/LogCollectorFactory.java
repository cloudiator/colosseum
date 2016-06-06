package components;

import com.google.inject.ImplementedBy;

/**
 * Created by daniel on 06.06.16.
 */
@ImplementedBy(LogCollectorFactoryImpl.class) public interface LogCollectorFactory {

    LogCollector forLogFile(LogFile logFile);

}
