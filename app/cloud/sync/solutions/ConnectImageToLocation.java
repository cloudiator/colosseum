package cloud.sync.solutions;

import cloud.CloudCredentialLocationId;
import cloud.ImageInCloudAndLocation;
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

        ImageInCloudAndLocation imageInCloudAndLocation =
            ((ImageProblems.ImageMissesLocation) problem).getImageInCloudAndLocation();

        CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(imageInCloudAndLocation.id());

        Image modelImage = this.imageModelService
            .getByUuidInCloudAndUuidOfCloudAndUuidOfLocation(cloudCredentialLocationId.baseId(),
                cloudCredentialLocationId.cloud());
        Location location = this.locationModelService
            .getByUuidInCloudAndUuidOfCloud(cloudCredentialLocationId.location(),
                cloudCredentialLocationId.cloud());
        if (modelImage == null || location == null) {
            throw new SolutionException();
        }

        modelImage.getLocations().add(location);
        imageModelService.save(modelImage);
    }
}
