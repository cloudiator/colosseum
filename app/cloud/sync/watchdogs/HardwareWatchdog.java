package cloud.sync.watchdogs;

import cloud.CloudService;
import cloud.resources.HardwareInLocation;
import cloud.sync.AbstractCloudServiceWatchdog;
import cloud.sync.Problem;
import cloud.sync.problems.HardwareProblems;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import components.execution.SimpleBlockingQueue;
import models.CloudCredential;
import models.Hardware;
import models.Location;
import models.service.HardwareModelService;

/**
 * Created by daniel on 07.05.15.
 */
public class HardwareWatchdog extends AbstractCloudServiceWatchdog {

    private final HardwareModelService hardwareModelService;

    @Inject protected HardwareWatchdog(CloudService cloudService,
        @Named(value = "problemQueue") SimpleBlockingQueue<Problem> simpleBlockingQueue,
        HardwareModelService hardwareModelService) {
        super(cloudService, simpleBlockingQueue);
        this.hardwareModelService = hardwareModelService;
    }



    @Override protected void watch(CloudService cloudService) {
        for (HardwareInLocation hardwareInLocation : cloudService.getDiscoveryService()
            .listHardwareFlavors()) {

            Hardware modelHardware = hardwareModelService.getByRemoteId(hardwareInLocation.id());

            if (modelHardware == null) {
                report(new HardwareProblems.HardwareNotInDatabase(hardwareInLocation));
            } else {
                CloudCredential credentialToSearchFor = null;
                for (CloudCredential cloudCredential : modelHardware.getCloudCredentials()) {
                    if (cloudCredential.getUuid().equals(hardwareInLocation.credential())) {
                        credentialToSearchFor = cloudCredential;
                        break;
                    }
                }

                if (credentialToSearchFor == null) {
                    report(new HardwareProblems.HardwareMissesCredential(hardwareInLocation));
                }

                Location locationToSearchFor = null;
                for (Location location : modelHardware.getLocations()) {
                    if (location.getCloud().getUuid().equals(hardwareInLocation.cloud()) && location
                        .getRemoteId().equals(hardwareInLocation.location())) {
                        locationToSearchFor = location;
                        break;
                    }
                }

                if (locationToSearchFor == null) {
                    report(new HardwareProblems.HardwareMissesLocation(hardwareInLocation));
                }
            }
        }
    }
}
