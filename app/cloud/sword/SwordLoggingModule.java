package cloud.sword;

import de.uniulm.omi.cloudiator.sword.api.logging.Logger;
import de.uniulm.omi.cloudiator.sword.api.logging.LoggerFactory;
import de.uniulm.omi.cloudiator.sword.core.logging.LoggingModule;

/**
 * Created by daniel on 06.05.15.
 */
public class SwordLoggingModule extends LoggingModule {

    @Override protected LoggerFactory getLoggerFactory() {
        return SwordLogger::new;
    }

    private static class SwordLogger implements Logger {

        private final play.Logger.ALogger logger;

        private SwordLogger(String s) {
            this.logger = play.Logger.of(s);
        }

        @Override public boolean isTraceEnabled() {
            return this.logger.isTraceEnabled();
        }

        @Override public boolean isDebugEnabled() {
            return this.logger.isDebugEnabled();
        }

        @Override public boolean isInfoEnabled() {
            return this.logger.isInfoEnabled();
        }

        @Override public boolean isWarnEnabled() {
            return this.logger.isWarnEnabled();
        }

        @Override public boolean isErrorEnabled() {
            return this.logger.isErrorEnabled();
        }

        @Override public boolean isFatalEnabled() {
            return this.logger.isErrorEnabled();
        }

        @Override public void trace(String s) {
            this.logger.trace(s);
        }

        @Override public void debug(String s) {
            this.logger.trace(s);
        }

        @Override public void info(String s) {
            this.logger.info(s);
        }

        @Override public void warn(String s) {
            this.logger.warn(s);
        }

        @Override public void warn(String s, Throwable throwable) {
            this.logger.warn(s, throwable);
        }

        @Override public void error(String s) {
            this.logger.error(s);
        }

        @Override public void error(String s, Throwable throwable) {
            this.logger.error(s, throwable);
        }

        @Override public void fatal(String s) {
            this.logger.error(s);
        }

        @Override public void fatal(String s, Throwable throwable) {
            this.logger.error(s, throwable);
        }
    }
}
