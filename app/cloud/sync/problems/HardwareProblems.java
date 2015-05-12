package cloud.sync.problems;

import cloud.HardwareInCloudAndLocation;
import cloud.sync.Problem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 05.05.15.
 */
public class HardwareProblems {

    private HardwareProblems() {

    }

    private abstract static class BaseHardwareProblem implements Problem {

        private final HardwareInCloudAndLocation hardwareInCloudAndLocation;

        public BaseHardwareProblem(HardwareInCloudAndLocation hardwareInCloudAndLocation) {
            checkNotNull(hardwareInCloudAndLocation);
            this.hardwareInCloudAndLocation = hardwareInCloudAndLocation;
        }

        @Override public int getPriority() {
            return Priority.MEDIUM;
        }

        public HardwareInCloudAndLocation getHardwareInCloudAndLocation() {
            return hardwareInCloudAndLocation;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseHardwareProblem))
                return false;

            BaseHardwareProblem that = (BaseHardwareProblem) o;

            return hardwareInCloudAndLocation.equals(that.hardwareInCloudAndLocation);

        }

        @Override public int hashCode() {
            return hardwareInCloudAndLocation.hashCode();
        }
    }


    public static class BaseHardwareNotInDatabase extends BaseHardwareProblem {
        public BaseHardwareNotInDatabase(HardwareInCloudAndLocation hardwareInCloudAndLocation) {
            super(hardwareInCloudAndLocation);
        }
    }


    public static class HardwareMissesCredential extends BaseHardwareProblem {
        public HardwareMissesCredential(HardwareInCloudAndLocation hardwareInCloudAndLocation) {
            super(hardwareInCloudAndLocation);
        }
    }


    public static class HardwareMissesLocation extends BaseHardwareProblem {
        public HardwareMissesLocation(HardwareInCloudAndLocation hardwareInCloudAndLocation) {
            super(hardwareInCloudAndLocation);
        }
    }


}
