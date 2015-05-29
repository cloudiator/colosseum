package cloud.sync.solutions;

import cloud.util.CloudScopedId;
import cloud.HardwareInCloudAndLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import models.Hardware;
import models.Location;
import models.service.api.HardwareModelService;
import models.service.api.LocationModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ConnectHardwareToLocation implements Solution {

    private final HardwareModelService hardwareModelService;
    private final LocationModelService locationModelService;

    @Inject public ConnectHardwareToLocation(HardwareModelService hardwareModelService,
        LocationModelService locationModelService) {
        this.hardwareModelService = hardwareModelService;
        this.locationModelService = locationModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof HardwareProblems.HardwareMissesLocation;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));

        HardwareInCloudAndLocation hardwareInCloudAndLocation =
            ((HardwareProblems.HardwareMissesLocation) problem).getHardwareInCloudAndLocation();

        CloudScopedId cloudScopedId =
            CloudScopedId.of(hardwareInCloudAndLocation.id());

        Hardware modelHardware = this.hardwareModelService
            .getByUuidInCloudAndUuidOfCloudAndUuidOfLocation(cloudScopedId.baseId(),
                cloudScopedId.cloud());
        Location location = this.locationModelService
            .getByUuidInCloudAndUuidOfCloud(cloudScopedId.location(),
                cloudScopedId.cloud());
        if (modelHardware == null || location == null) {
            throw new SolutionException();
        }

        modelHardware.getLocations().add(location);
        hardwareModelService.save(modelHardware);
    }
}
