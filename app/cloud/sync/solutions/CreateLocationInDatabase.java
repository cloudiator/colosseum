package cloud.sync.solutions;

import cloud.CloudCredentialLocationId;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.LocationProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Location;
import models.repository.api.generic.ModelRepository;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 04.05.15.
 */
public class CreateLocationInDatabase implements Solution {

    private final ModelRepository<Location> locationModelRepository;
    private final ModelRepository<Cloud> cloudModelRepository;

    @Inject public CreateLocationInDatabase(ModelRepository<Location> locationModelRepository,
        ModelRepository<Cloud> cloudModelRepository) {
        this.locationModelRepository = locationModelRepository;
        this.cloudModelRepository = cloudModelRepository;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof LocationProblems.BaseLocationNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        LocationProblems.BaseLocationNotInDatabase baseLocationNotInDatabase =
            (LocationProblems.BaseLocationNotInDatabase) problem;

        CloudCredentialLocationId cloudCredentialLocationId =
            CloudCredentialLocationId.of(baseLocationNotInDatabase.getLocationInCloud().id());
        Cloud cloud = cloudModelRepository.findByUuid(cloudCredentialLocationId.cloud());

        String cloudUuid = cloudCredentialLocationId.baseId();
        Location location = new Location(cloud, cloudUuid, null, null, null,
            baseLocationNotInDatabase.getLocationInCloud().isAssignable());

        this.locationModelRepository.save(location);
    }


}
