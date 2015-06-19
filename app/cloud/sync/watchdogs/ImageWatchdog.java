package cloud.sync.watchdogs;

import cloud.util.CloudScopedId;
import cloud.ImageInCloudAndLocation;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import components.execution.SimpleBlockingQueue;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import com.google.inject.name.Named;
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

    @Inject protected ImageWatchdog(ComputeService computeService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue,
        ImageModelService imageModelService) {
        super(computeService, simpleBlockingQueue);
        this.imageModelService = imageModelService;
    }



    @Override protected void watch(ComputeService computeService) {
        for (Image image : computeService.listImages()) {
            if (image instanceof ImageInCloudAndLocation) {
                final CloudScopedId cloudScopedId =
                    CloudScopedId.of(image.id());

                models.Image modelImage = imageModelService
                    .getByUuidInCloudAndUuidOfCloudAndUuidOfLocation(
                        cloudScopedId.baseId(), cloudScopedId.cloud());

                if (modelImage == null) {
                    report(
                        new ImageProblems.ImageNotInDatabase((ImageInCloudAndLocation) image));
                } else {
                    CloudCredential credentialToSearchFor = null;
                    for (CloudCredential cloudCredential : modelImage.getCloudCredentials()) {
                        if (cloudCredential.getUuid()
                            .equals(cloudScopedId.credential())) {
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
                        if (location.getCloud().getUuid().equals(cloudScopedId.cloud())
                            && location.getCloudUuid()
                            .equals(cloudScopedId.location())) {
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
