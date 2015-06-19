package cloud.sync.problems;

import cloud.resources.ImageInLocation;
import cloud.sync.Problem;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 05.05.15.
 */
public class ImageProblems {

    private ImageProblems() {

    }

    private abstract static class BaseImageProblem implements Problem {

        private final ImageInLocation imageInLocation;

        public BaseImageProblem(ImageInLocation imageInLocation) {
            checkNotNull(imageInLocation);
            this.imageInLocation = imageInLocation;
        }

        @Override public int getPriority() {
            return Priority.MEDIUM;
        }

        public ImageInLocation getImageInLocation() {
            return imageInLocation;
        }

        @Override public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof BaseImageProblem))
                return false;

            BaseImageProblem that = (BaseImageProblem) o;

            return imageInLocation.equals(that.imageInLocation);

        }

        @Override public int hashCode() {
            return imageInLocation.hashCode();
        }
    }


    public static class ImageNotInDatabase extends BaseImageProblem {

        public ImageNotInDatabase(ImageInLocation imageInLocation) {
            super(imageInLocation);
        }
    }


    public static class ImageMissesCredential extends BaseImageProblem {
        public ImageMissesCredential(ImageInLocation imageInLocation) {
            super(imageInLocation);
        }
    }


    public static class ImageMissesLocation extends BaseImageProblem {
        public ImageMissesLocation(ImageInLocation imageInLocation) {
            super(imageInLocation);
        }
    }


}
