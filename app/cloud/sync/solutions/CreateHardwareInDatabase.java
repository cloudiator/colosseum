package cloud.sync.solutions;

import cloud.sync.Problem;
import cloud.sync.Solution;
import cloud.sync.SolutionException;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import models.Cloud;
import models.Hardware;
import models.HardwareOffer;
import models.service.ModelService;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 07.05.15.
 */
public class CreateHardwareInDatabase implements Solution {

    private final ModelService<Hardware> hardwareModelService;
    private final ModelService<HardwareOffer> hardwareOfferModelService;
    private final ModelService<Cloud> cloudModelService;

    @Inject public CreateHardwareInDatabase(ModelService<Hardware> hardwareModelService,
        ModelService<HardwareOffer> hardwareOfferModelService,
        ModelService<Cloud> cloudModelService) {
        this.hardwareModelService = hardwareModelService;
        this.hardwareOfferModelService = hardwareOfferModelService;
        this.cloudModelService = cloudModelService;
    }

    @Override public boolean isSolutionFor(Problem problem) {
        return problem instanceof HardwareProblems.HardwareNotInDatabase;
    }

    @Override public void applyTo(Problem problem) throws SolutionException {
        checkArgument(isSolutionFor(problem));

        HardwareProblems.HardwareNotInDatabase hardwareNotInDatabase =
            (HardwareProblems.HardwareNotInDatabase) problem;

        Cloud cloud =
            cloudModelService.getByUuid(hardwareNotInDatabase.getHardwareInLocation().cloud());
        if (cloud == null) {
            throw new SolutionException();
        }

        Hardware hardware = new Hardware(hardwareNotInDatabase.getHardwareInLocation().id(), cloud,
            getHardwareOffer(hardwareNotInDatabase.getHardwareInLocation().numberOfCores(),
                hardwareNotInDatabase.getHardwareInLocation().mbRam(), null),
            hardwareNotInDatabase.getHardwareInLocation().name());
        hardware
            .setCloudProviderId(hardwareNotInDatabase.getHardwareInLocation().cloudProviderId());

        hardwareModelService.save(hardware);

    }

    private HardwareOffer getHardwareOffer(Integer numberOfCores, Long mbOfRam,
        @Nullable Long localDiskSpace) {

        for (HardwareOffer hardwareOffer : hardwareOfferModelService.getAll()) {
            if (hardwareOffer.getNumberOfCores().equals(numberOfCores)) {
                if (hardwareOffer.getMbOfRam().equals(mbOfRam)) {
                    if (localDiskSpace == null || localDiskSpace
                        .equals(hardwareOffer.getLocalDiskSpace())) {
                        return hardwareOffer;
                    }
                }
            }
        }

        HardwareOffer hardwareOffer = new HardwareOffer(numberOfCores, mbOfRam, localDiskSpace);
        this.hardwareOfferModelService.save(hardwareOffer);

        return hardwareOffer;
    }
}
