package components.execution;

/**
 * Created by daniel on 08.05.15.
 */
public interface Prioritized {

    class Priority {
        public static int HIGH = 2;
        public static int MEDIUM = 1;
        public static int LOW = 0;
    }

    int getPriority();

}
