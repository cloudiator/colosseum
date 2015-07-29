package cloud.sync.problems;

import cloud.resources.HardwareInLocation;
import cloud.sync.Problem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 05.05.15.
 */
public class HardwareProblems {

    private HardwareProblems() {

    }

    private abstract static class BaseHardwareProblem implements Problem {

        private final HardwareInLocation hardwareInLocation;

        public BaseHardwareProblem(HardwareInLocation hardwareInLocation) {
            checkNotNull(hardwareInLocation);
            this.hardwareInLocation = hardwareInLocation;
        }

        @Override public int getPriority() {
            return Priority.MEDIUM;
        }

        public HardwareInLocation getHardwareInLocation() {
            return hardwareInLocation;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseHardwareProblem))
                return false;

            BaseHardwareProblem that = (BaseHardwareProblem) o;

            return hardwareInLocation.equals(that.hardwareInLocation);

        }

        @Override public int hashCode() {
            return hardwareInLocation.hashCode();
        }
    }


    public static class HardwareNotInDatabase extends BaseHardwareProblem {

        public HardwareNotInDatabase(HardwareInLocation hardwareInLocation) {
            super(hardwareInLocation);
        }
    }


    public static class HardwareMissesCredential extends BaseHardwareProblem {

        public HardwareMissesCredential(HardwareInLocation hardwareInLocation) {
            super(hardwareInLocation);
        }
    }


    public static class HardwareMissesLocation extends BaseHardwareProblem {
        public HardwareMissesLocation(HardwareInLocation hardwareInLocation) {
            super(hardwareInLocation);
        }
    }


}
