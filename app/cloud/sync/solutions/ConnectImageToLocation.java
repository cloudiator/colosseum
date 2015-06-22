package cloud.sync.solutions;

import cloud.resources.ImageInLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import models.Image;
import models.Location;
import models.service.api.ImageModelService;
import models.service.api.LocationModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ConnectImageToLocation implements Solution {

    private final ImageModelService imageModelService;
    private final LocationModelService locationModelService;

    @Inject public ConnectImageToLocation(ImageModelService imageModelService,
        LocationModelService locationModelService) {
        this.imageModelService = imageModelService;
        this.locationModelService = locationModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof ImageProblems.ImageMissesLocation;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));



        ImageInLocation imageInLocation =
            ((ImageProblems.ImageMissesLocation) problem).getImageInLocation();

        Image modelImage = imageModelService.getByRemoteId(imageInLocation.id());
        Location location = this.locationModelService.getByRemoteId(imageInLocation.location());
        if (modelImage == null || location == null) {
            throw new SolutionException();
        }

        modelImage.getLocations().add(location);
        imageModelService.save(modelImage);
    }
}
