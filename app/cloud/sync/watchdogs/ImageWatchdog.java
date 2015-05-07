package cloud.sync.watchdogs;

import cloud.CloudCredentialLocationId;
import cloud.ImageInCloudAndLocation;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.ProblemQueue;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.domain.Image;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.CloudCredential;
import models.Location;
import models.service.api.ImageModelService;

/**
 * Created by daniel on 07.05.15.
 */
public class ImageWatchdog extends AbstractCloudServiceWatchdog {

    private final ImageModelService imageModelService;

    @Inject protected ImageWatchdog(ComputeService computeService, ProblemQueue problemQueue,
        ImageModelService imageModelService) {
        super(computeService, problemQueue);
        this.imageModelService = imageModelService;
    }

    @Override protected void watch(ComputeService computeService) {
        for (Image image : computeService.listImages()) {
            if (image instanceof ImageInCloudAndLocation) {
                final CloudCredentialLocationId cloudCredentialLocationId =
                    CloudCredentialLocationId.of(image.id());

                models.Image modelImage = imageModelService
                    .getByUuidInCloudAndUuidOfCloudAndUuidOfLocation(
                        cloudCredentialLocationId.baseId(), cloudCredentialLocationId.cloud());

                if (modelImage == null) {
                    report(
                        new ImageProblems.BaseImageNotInDatabase((ImageInCloudAndLocation) image));
                } else {
                    CloudCredential credentialToSearchFor = null;
                    for (CloudCredential cloudCredential : modelImage.getCloudCredentials()) {
                        if (cloudCredential.getUuid()
                            .equals(cloudCredentialLocationId.credential())) {
                            credentialToSearchFor = cloudCredential;
                            break;
                        }
                    }

                    if (credentialToSearchFor == null) {
                        report(new ImageProblems.ImageMissesCredential(
                            (ImageInCloudAndLocation) image));
                    }

                    Location locationToSearchFor = null;
                    for (Location location : modelImage.getLocations()) {
                        if (location.getCloud().getUuid().equals(cloudCredentialLocationId.cloud())
                            && location.getCloudUuid()
                            .equals(cloudCredentialLocationId.location())) {
                            locationToSearchFor = location;
                            break;
                        }
                    }

                    if (locationToSearchFor == null) {
                        report(
                            new ImageProblems.ImageMissesLocation((ImageInCloudAndLocation) image));
                    }
                }


            }
        }
    }
}
