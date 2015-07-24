package cloud.sync.solutions;

import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.LocationProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Location;
import models.service.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 04.05.15.
 */
public class CreateLocationInDatabase implements Solution {

    private final ModelService<Location> locationModelService;
    private final ModelService<Cloud> cloudModelService;

    @Inject public CreateLocationInDatabase(ModelService<Location> locationModelService,
        ModelService<Cloud> cloudModelService) {
        this.locationModelService = locationModelService;
        this.cloudModelService = cloudModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof LocationProblems.LocationNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        LocationProblems.LocationNotInDatabase locationNotInDatabase =
            (LocationProblems.LocationNotInDatabase) problem;

        Cloud cloud =
            cloudModelService.getByUuid(locationNotInDatabase.getLocationInCloud().cloud());

        String remoteId = locationNotInDatabase.getLocationInCloud().id();
        Location location = new Location(cloud, remoteId, null, null, null,
            locationNotInDatabase.getLocationInCloud().isAssignable());
        location.setCloudProviderId(locationNotInDatabase.getLocationInCloud().cloudProviderId());

        this.locationModelService.save(location);
    }


}
