package cloud.sync.problems;

import cloud.ImageInCloudAndLocation;
import cloud.sync.Problem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 05.05.15.
 */
public class ImageProblems {

    private ImageProblems() {

    }

    private abstract static class BaseImageProblem implements Problem {

        private final ImageInCloudAndLocation imageInCloudAndLocation;

        public BaseImageProblem(ImageInCloudAndLocation imageInCloudAndLocation) {
            checkNotNull(imageInCloudAndLocation);
            this.imageInCloudAndLocation = imageInCloudAndLocation;
        }

        @Override public int getPriority() {
            return Priority.MEDIUM;
        }

        public ImageInCloudAndLocation getImageInCloudAndLocation() {
            return imageInCloudAndLocation;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseImageProblem))
                return false;

            BaseImageProblem that = (BaseImageProblem) o;

            return imageInCloudAndLocation.equals(that.imageInCloudAndLocation);

        }

        @Override public int hashCode() {
            return imageInCloudAndLocation.hashCode();
        }
    }


    public static class BaseImageNotInDatabase extends BaseImageProblem {

        public BaseImageNotInDatabase(ImageInCloudAndLocation imageInCloudAndLocation) {
            super(imageInCloudAndLocation);
        }
    }


    public static class ImageMissesCredential extends BaseImageProblem {

        public ImageMissesCredential(ImageInCloudAndLocation imageInCloudAndLocation) {
            super(imageInCloudAndLocation);
        }
    }


    public static class ImageMissesLocation extends BaseImageProblem {

        public ImageMissesLocation(ImageInCloudAndLocation imageInCloudAndLocation) {
            super(imageInCloudAndLocation);
        }
    }


}
