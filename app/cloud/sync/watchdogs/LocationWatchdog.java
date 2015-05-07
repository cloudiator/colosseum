package cloud.sync.watchdogs;

import cloud.CloudCredentialLocationId;
import cloud.LocationInCloud;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.ProblemQueue;
import cloud.sync.problems.LocationProblems;
import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.CloudCredential;
import models.Location;
import models.service.api.LocationModelService;

/**
 * Created by daniel on 05.05.15.
 */
public class LocationWatchdog extends AbstractCloudServiceWatchdog {

    private final LocationModelService locationModelService;

    @Inject protected LocationWatchdog(ComputeService computeService, ProblemQueue problemQueue,
        LocationModelService locationModelService) {
        super(computeService, problemQueue);
        this.locationModelService = locationModelService;
    }

    @Override protected void watch(ComputeService computeService) {

        for (de.uniulm.omi.cloudiator.sword.api.domain.Location location : computeService
            .listLocations()) {
            if (location instanceof LocationInCloud) {
                final CloudCredentialLocationId cloudCredentialLocationId =
                    CloudCredentialLocationId.of(location.id());

                Location modelLocation = locationModelService
                    .getByUuidInCloudAndUuidOfCloud(cloudCredentialLocationId.baseId(),
                        cloudCredentialLocationId.cloud());

                if (modelLocation == null) {
                    this.report(
                        new LocationProblems.BaseLocationNotInDatabase((LocationInCloud) location));
                } else {
                    CloudCredential credentialToSearchFor = null;
                    for (CloudCredential cloudCredential : modelLocation.getCloudCredentials()) {
                        if (cloudCredential.getUuid()
                            .equals(cloudCredentialLocationId.credential())) {
                            credentialToSearchFor = cloudCredential;
                            break;
                        }
                    }

                    if (credentialToSearchFor == null) {
                        this.report(new LocationProblems.LocationMissesCredential(
                            (LocationInCloud) location));
                    }
                }
            }
        }
    }

}
