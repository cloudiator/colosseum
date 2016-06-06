package components;

/**
 * Created by daniel on 06.06.16.
 */
public interface LogFile {

    public enum LogType {
        // a local log
        LOCAL,
        // a log on each vm
        VM
    }

    LogType logType();

    String location();
}
