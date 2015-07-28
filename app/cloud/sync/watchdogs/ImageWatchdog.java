package cloud.sync.watchdogs;

import cloud.CloudService;
import cloud.resources.ImageInLocation;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import cloud.sync.problems.ImageProblems;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import models.CloudCredential;
import models.Location;
import models.service.ImageModelService;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 07.05.15.
 */
public class ImageWatchdog extends AbstractCloudServiceWatchdog {

    private final ImageModelService imageModelService;

    @Inject protected ImageWatchdog(CloudService cloudService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue,
        ImageModelService imageModelService) {
        super(cloudService, simpleBlockingQueue);
        this.imageModelService = imageModelService;
    }



    @Override protected void watch(CloudService cloudService) {
        for (ImageInLocation imageInLocation : cloudService.getDiscoveryService().listImages()) {

            models.Image modelImage = imageModelService.getByRemoteId(imageInLocation.id());

            if (modelImage == null) {
                report(new ImageProblems.ImageNotInDatabase(imageInLocation));
            } else {
                CloudCredential credentialToSearchFor = null;
                for (CloudCredential cloudCredential : modelImage.getCloudCredentials()) {
                    if (cloudCredential.getUuid().equals(imageInLocation.credential())) {
                        credentialToSearchFor = cloudCredential;
                        break;
                    }
                }

                if (credentialToSearchFor == null) {
                    report(new ImageProblems.ImageMissesCredential(imageInLocation));
                }

                Location locationToSearchFor = null;
                for (Location location : modelImage.getLocations()) {
                    if (location.getCloud().getUuid().equals(imageInLocation.cloud()) && location
                        .getRemoteId().equals(imageInLocation.location())) {
                        locationToSearchFor = location;
                        break;
                    }
                }

                if (locationToSearchFor == null) {
                    report(new ImageProblems.ImageMissesLocation(imageInLocation));
                }
            }



        }
    }

    @Override public long period() {
        return 1;
    }

    @Override public long delay() {
        return 0;
    }

    @Override public TimeUnit timeUnit() {
        return TimeUnit.MINUTES;
    }
}
