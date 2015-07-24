package cloud.sync.watchdogs;

import cloud.CloudService;
import cloud.resources.LocationInCloud;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import cloud.sync.problems.LocationProblems;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import models.CloudCredential;
import models.Location;
import models.service.LocationModelService;

/**
 * Created by daniel on 05.05.15.
 */
public class LocationWatchdog extends AbstractCloudServiceWatchdog {

    private final LocationModelService locationModelService;

    @Inject protected LocationWatchdog(CloudService cloudService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue,
        LocationModelService locationModelService) {
        super(cloudService, simpleBlockingQueue);
        this.locationModelService = locationModelService;
    }



    @Override protected void watch(CloudService cloudService) {

        for (LocationInCloud location : cloudService.getDiscoveryService().listLocations()) {
            Location modelLocation = locationModelService.getByRemoteId(location.id());

            if (modelLocation == null) {
                this.report(new LocationProblems.LocationNotInDatabase(location));
            } else {
                CloudCredential credentialToSearchFor = null;
                for (CloudCredential cloudCredential : modelLocation.getCloudCredentials()) {
                    if (cloudCredential.getUuid().equals(location.credential())) {
                        credentialToSearchFor = cloudCredential;
                        break;
                    }
                }

                if (credentialToSearchFor == null) {
                    this.report(new LocationProblems.LocationMissesCredential(location));
                }
            }

        }
    }

}
