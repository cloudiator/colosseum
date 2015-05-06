package cloud.sync;

/**
 * Created by daniel on 04.05.15.
 */
public interface Problem {

    class Priority {
        public static int HIGH = 2;
        public static int MEDIUM = 1;
        public static int LOW = 0;
    }

    int getPriority();
}
