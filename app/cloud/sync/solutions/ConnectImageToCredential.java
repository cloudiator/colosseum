package cloud.sync.solutions;

import cloud.ScopedId;
import cloud.ImageInCloudAndLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import models.CloudCredential;
import models.Image;
import models.service.api.ImageModelService;
import models.service.api.generic.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ConnectImageToCredential implements Solution {

    private final ImageModelService imageModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject public ConnectImageToCredential(ImageModelService imageModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {

        this.imageModelService = imageModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof ImageProblems.ImageMissesCredential;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        ImageInCloudAndLocation imageInCloudAndLocation =
            ((ImageProblems.ImageMissesCredential) problem).getImageInCloudAndLocation();
        ScopedId scopedId =
            ScopedId.of(imageInCloudAndLocation.id());

        Image modelImage = imageModelService
            .getByUuidInCloudAndUuidOfCloudAndUuidOfLocation(scopedId.baseId(),
                scopedId.cloud());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(scopedId.credential());

        if (modelImage == null || cloudCredential == null) {
            throw new SolutionException();
        }

        modelImage.getCloudCredentials().add(cloudCredential);
        imageModelService.save(modelImage);
    }



}
