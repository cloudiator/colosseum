package cloud;

import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.domain.HardwareFlavor;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.*;
import models.service.api.generic.ModelService;
import play.db.jpa.Transactional;

/**
 * Created by daniel on 29.04.15.
 */
@Transactional public class HardwareWatcher extends AbstractCloudServiceWatcher {

    private final ModelService<Hardware> hardwareModelService;
    private final ModelService<HardwareOffer> hardwareOfferModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<Location> locationModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject protected HardwareWatcher(ComputeService computeService,
        ModelService<Hardware> hardwareModelService,
        ModelService<HardwareOffer> hardwareOfferModelService,
        ModelService<Cloud> cloudModelService, ModelService<Location> locationModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(computeService);
        this.hardwareModelService = hardwareModelService;
        this.hardwareOfferModelService = hardwareOfferModelService;
        this.cloudModelService = cloudModelService;
        this.locationModelService = locationModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }


    private boolean inDatabase(HardwareInCloudAndLocation hardwareInCloudAndLocation) {
        // the image is already in the database if:
        // an image with the same cloudUuid exists
        // this image has the same cloud
        // this image has the same location
        // this image has the same cloud credential

        CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(hardwareInCloudAndLocation.id());

        for (Hardware hardware : hardwareModelService.getAll()) {
            //check for clouduuid
            if (hardware.getCloudUuid().equals(cloudCredentialLocationId.rawId())) {
                //check for cloud
                if (hardware.getCloud().getUuid().equals(cloudCredentialLocationId.cloud())) {
                    //check for location
                    for (Location location : hardware.getLocations()) {
                        if (location.getCloudUuid().equals(cloudCredentialLocationId.location())) {
                            //check for credential
                            for (CloudCredential cloudCredential : hardware.getCloudCredentials()) {
                                if (cloudCredential.getUuid()
                                    .equals(cloudCredentialLocationId.credential())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void createInDatabase(HardwareInCloudAndLocation hardwareInCloudAndLocation) {

        final CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(hardwareInCloudAndLocation.id());
        Cloud cloud = cloudModelService.getByUuid(cloudCredentialLocationId.cloud());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(hardwareInCloudAndLocation.credential());
        Location location = null;
        for (Location findLocation : locationModelService.getAll()) {
            if (findLocation.getCloud().equals(cloud) && findLocation.getCloudUuid()
                .equals(cloudCredentialLocationId.location())) {
                location = findLocation;
                break;
            }
        }
        if (cloud == null || cloudCredential == null || location == null) {
            return;
        }

        // check if the hardware itself is present, done by checking for cloud and id.
        Hardware hardware = null;
        for (Hardware hardwareToFind : hardwareModelService.getAll()) {
            if (hardwareToFind.getCloud().equals(cloud) && hardwareToFind.getCloudUuid()
                .equals(cloudCredentialLocationId.rawId())) {
                hardware = hardwareToFind;
                break;
            }
        }

        if (hardware == null) {

            //check if we find the correct hardwareoffer
            HardwareOffer hardwareOffer = null;
            for (HardwareOffer hardwareOfferToFind : hardwareOfferModelService.getAll()) {
                if (hardwareOfferToFind.getMbOfRam() == hardwareInCloudAndLocation.mbRam()
                    && hardwareOfferToFind.getNumberOfCores() == hardwareInCloudAndLocation
                    .numberOfCores()) {
                    hardwareOffer = hardwareOfferToFind;
                    break;
                }
            }

            if (hardwareOffer == null) {
                hardwareOffer = new HardwareOffer(hardwareInCloudAndLocation.numberOfCores(),
                    hardwareInCloudAndLocation.mbRam(), null);
                hardwareOfferModelService.save(hardwareOffer);
            }

            hardware =
                new Hardware(cloudCredentialLocationId.rawId(), cloud, hardwareOffer, location);
            hardwareModelService.save(hardware);
        }

        //check if we need to add credentials
        if (!hardware.getCloudCredentials().contains(cloudCredential)) {
            hardware.getCloudCredentials().add(cloudCredential);
            hardwareModelService.save(hardware);
        }

        //check if we need to add location
        if (!hardware.getLocations().contains(location)) {
            hardware.getLocations().add(location);
            hardwareModelService.save(hardware);
        }


    }

    @Override protected void watch(ComputeService computeService) {
        for (HardwareFlavor hardwareFlavor : computeService.listHardwareFlavors()) {
            if (hardwareFlavor instanceof HardwareInCloudAndLocation) {
                if (!inDatabase((HardwareInCloudAndLocation) hardwareFlavor)) {
                    createInDatabase((HardwareInCloudAndLocation) hardwareFlavor);
                }
            }
        }
    }
}
