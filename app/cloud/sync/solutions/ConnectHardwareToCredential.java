package cloud.sync.solutions;

import cloud.util.CloudScopedId;
import cloud.HardwareInCloudAndLocation;
import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import models.CloudCredential;
import models.Hardware;
import models.service.api.HardwareModelService;
import models.service.api.generic.ModelService;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class ConnectHardwareToCredential implements Solution {

    private final HardwareModelService hardwareModelService;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    @Inject public ConnectHardwareToCredential(HardwareModelService hardwareModelService,
        ModelService<CloudCredential> cloudCredentialModelService) {

        this.hardwareModelService = hardwareModelService;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof HardwareProblems.HardwareMissesCredential;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));
        HardwareInCloudAndLocation hardwareInCloudAndLocation =
            ((HardwareProblems.HardwareMissesCredential) problem).getHardwareInCloudAndLocation();
        CloudScopedId scopedId =
            CloudScopedId.of(hardwareInCloudAndLocation.id());

        Hardware modelHardware = hardwareModelService
            .getByUuidInCloudAndUuidOfCloudAndUuidOfLocation(scopedId.baseId(),
                scopedId.cloud());
        CloudCredential cloudCredential =
            cloudCredentialModelService.getByUuid(scopedId.credential());

        if (modelHardware == null || cloudCredential == null) {
            throw new SolutionException();
        }

        modelHardware.getCloudCredentials().add(cloudCredential);
        hardwareModelService.save(modelHardware);

    }



}
