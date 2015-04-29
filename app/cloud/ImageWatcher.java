package cloud;

import com.google.inject.Inject;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.Cloud;
import models.CloudCredential;
import models.Image;
import models.Location;
import models.service.api.generic.ModelService;
import play.db.jpa.Transactional;

/**
 * Created by daniel on 29.04.15.
 */
@Transactional public class ImageWatcher extends AbstractCloudServiceWatcher {

    private final ModelService<Image> imageModelService;
    private final ModelService<Cloud> cloudModelService;
    private final ModelService<Location> locationModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject
    protected ImageWatcher(ComputeService computeService, ModelService<Image> imageModelService,
        ModelService<Cloud> cloudModelService, ModelService<Location> locationModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        super(computeService);
        this.imageModelService = imageModelService;
        this.cloudModelService = cloudModelService;
        this.locationModelService = locationModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }


    private boolean inDatabase(ImageInCloudAndLocation imageInCloudAndLocation) {
        // the image is already in the database if:
        // an image with the same cloudUuid exists
        // this image has the same cloud
        // this image has the same location
        // this image has the same cloud credential

        CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(imageInCloudAndLocation.id());

        for (Image image : imageModelService.getAll()) {
            //check for clouduuid
            if (image.getCloudUuid().equals(cloudCredentialLocationId.rawId())) {
                //check for cloud
                if (image.getCloud().getUuid().equals(cloudCredentialLocationId.cloud())) {
                    //check for location
                    for (Location location : image.getLocations()) {
                        if (location.getCloudUuid().equals(cloudCredentialLocationId.location())) {
                            //check for credential
                            for (CloudCredential cloudCredential : image.getCloudCredentials()) {
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

    private void createInDatabase(ImageInCloudAndLocation imageInCloudAndLocation) {

        final CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(imageInCloudAndLocation.id());
        Cloud cloud = cloudModelService.getByUuid(cloudCredentialLocationId.cloud());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(imageInCloudAndLocation.credential());
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

        // check if the image itself is present, done by checking for cloud and id.
        Image image = null;
        for (Image imageToFind : imageModelService.getAll()) {
            if (imageToFind.getCloud().equals(cloud) && imageToFind.getCloudUuid()
                .equals(cloudCredentialLocationId.rawId())) {
                image = imageToFind;
                break;
            }
        }

        if (image == null) {
            image = new Image(cloudCredentialLocationId.rawId(), cloud, null, location);
            imageModelService.save(image);
        }

        //check if we need to add credentials
        if (!image.getCloudCredentials().contains(cloudCredential)) {
            image.getCloudCredentials().add(cloudCredential);
            imageModelService.save(image);
        }

        //check if we need to add location
        if (!image.getLocations().contains(location)) {
            image.getLocations().add(location);
            imageModelService.save(image);
        }


    }

    @Override protected void watch(ComputeService computeService) {
        for (de.uniulm.omi.cloudiator.sword.api.domain.Image image : computeService.listImages()) {
            if (image instanceof ImageInCloudAndLocation) {
                if (!inDatabase((ImageInCloudAndLocation) image)) {
                    createInDatabase((ImageInCloudAndLocation) image);
                }
            }
        }
    }
}
