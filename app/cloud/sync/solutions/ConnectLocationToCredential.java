package cloud.sync.solutions;

import cloud.util.CloudScopedId;
import cloud.LocationInCloud;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.LocationProblems;
import com.google.inject.Inject;
import models.CloudCredential;
import models.Location;
import models.service.api.LocationModelService;
import models.service.api.generic.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ConnectLocationToCredential implements Solution {

    private final LocationModelService locationModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject public ConnectLocationToCredential(LocationModelService locationModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {
        this.locationModelService = locationModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof LocationProblems.LocationMissesCredential;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));

        LocationInCloud locationInCloud =
            ((LocationProblems.LocationMissesCredential) problem).getLocationInCloud();
        final CloudScopedId cloudScopedId =
            CloudScopedId.of(locationInCloud.id());

        Location location = locationModelService
            .getByUuidInCloudAndUuidOfCloud(cloudScopedId.baseId(),
                cloudScopedId.cloud());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(cloudScopedId.credential());

        if (location == null || cloudCredential == null) {
            throw new SolutionException();
        }

        location.getCloudCredentials().add(cloudCredential);
        locationModelService.save(location);
    }
}
