package components.execution;

/**
 * Created by daniel on 08.05.15.
 */
public interface Prioritized {

    class Priority {
        public static final int HIGH = 2;
        public static final int MEDIUM = 1;
        public static final int LOW = 0;
    }

    int getPriority();

}
