package cloud.sync.solutions;

import cloud.resources.HardwareInLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import models.Hardware;
import models.Location;
import models.service.HardwareModelService;
import models.service.LocationModelService;

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

        HardwareInLocation hardwareInLocation =
            ((HardwareProblems.HardwareMissesLocation) problem).getHardwareInLocation();

        Hardware modelHardware = this.hardwareModelService.getByRemoteId(hardwareInLocation.id());
        Location location = this.locationModelService.getByRemoteId(hardwareInLocation.location());
        if (modelHardware == null || location == null) {
            throw new SolutionException();
        }
        modelHardware.getLocations().add(location);
        hardwareModelService.save(modelHardware);
    }
}
