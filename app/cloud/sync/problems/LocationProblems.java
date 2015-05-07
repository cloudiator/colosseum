package cloud.sync.problems;

import cloud.LocationInCloud;
import cloud.sync.Problem;

/**
 * Created by daniel on 05.05.15.
 */
public class LocationProblems {

    private LocationProblems() {

    }

    private abstract static class BaseLocationProblem implements Problem {
        private final LocationInCloud locationInCloud;

        public BaseLocationProblem(LocationInCloud locationInCloud) {
            this.locationInCloud = locationInCloud;
        }

        @Override public int getPriority() {
            return Priority.HIGH;
        }

        public LocationInCloud getLocationInCloud() {
            return locationInCloud;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseLocationProblem))
                return false;

            BaseLocationProblem that = (BaseLocationProblem) o;

            return locationInCloud.equals(that.locationInCloud);

        }

        @Override public int hashCode() {
            return locationInCloud.hashCode();
        }
    }


    public static class BaseLocationNotInDatabase extends BaseLocationProblem {
        public BaseLocationNotInDatabase(LocationInCloud locationInCloud) {
            super(locationInCloud);
        }
    }


    public static class LocationMissesCredential extends BaseLocationProblem {
        public LocationMissesCredential(LocationInCloud locationInCloud) {
            super(locationInCloud);
        }
    }


}
