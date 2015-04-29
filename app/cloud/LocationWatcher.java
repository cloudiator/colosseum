package cloud;

import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.Cloud;
import models.CloudCredential;
import models.Location;
import models.service.api.generic.ModelService;
import play.db.jpa.Transactional;

/**
 * Created by daniel on 17.04.15.
 */
@Transactional public class LocationWatcher extends AbstractCloudServiceWatcher {

    private final ModelService<Location> locationModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject protected LocationWatcher(ComputeService computeService,
        ModelService<Location> locationModelService, ModelService<Cloud> cloudModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(computeService);
        this.locationModelService = locationModelService;
        this.cloudModelService = cloudModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    private boolean inDatabaseBase(LocationInCloud locationInCloud) {
        for (Location location : locationModelService.getAll()) {
            CloudCredentialLocationId cloudCredentialLocationId =
                CloudCredentialLocationId.of(locationInCloud.id());
            if (cloudCredentialLocationId.cloud().equals(location.getCloud().getUuid())
                && cloudCredentialLocationId.rawId().equals(location.getCloudUuid())) {
                for (CloudCredential cloudCredential : location.getCloudCredentials()) {
                    if (cloudCredentialLocationId.credential().equals(cloudCredential.getUuid())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createInDatabase(LocationInCloud locationInCloud) {
        Cloud cloud = cloudModelService.getByUuid(locationInCloud.cloud());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(locationInCloud.credential());
        final CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(locationInCloud.id());

        //check if we can find the location, so we only need to update the credentials
        for (Location location : locationModelService.getAll()) {
            if (location.getCloud().equals(cloud) && location.getCloudUuid()
                .equals(cloudCredentialLocationId.rawId())) {
                location.getCloudCredentials().add(cloudCredential);
                locationModelService.save(location);
                return;
            }
        }

        //otherwise create a new one
        Location location = new Location(cloud, cloudCredentialLocationId.rawId(), null, null, null,
            locationInCloud.isAssignable());
        locationModelService.save(location);
        location.getCloudCredentials().add(cloudCredential);
        locationModelService.save(location);

    }



    @Override protected void watch(ComputeService computeService) {
        for (de.uniulm.omi.cloudiator.sword.api.domain.Location location : computeService
            .listLocations()) {
            if (location instanceof LocationInCloud) {
                if (!inDatabaseBase((LocationInCloud) location)) {
                    createInDatabase((LocationInCloud) location);
                }
            }
        }
    }
}
