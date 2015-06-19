package cloud;

import cloud.resources.HardwareInLocation;
import cloud.resources.ImageInLocation;
import cloud.resources.LocationInCloud;
import cloud.resources.VirtualMachineInLocation;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import de.uniulm.omi.cloudiator.sword.api.service.ComputeService;
import models.CloudCredential;
import models.service.api.generic.ModelService;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by daniel on 19.06.15.
 */
public class ComputeServiceHolder implements
    Iterable<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> {

    private Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>>
        computeServices;
    private final ComputeServiceFactory computeServiceFactory;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    ComputeServiceHolder(ComputeServiceFactory computeServiceFactory,
        ModelService<CloudCredential> cloudCredentialModelService) {
        this.computeServiceFactory = computeServiceFactory;
        this.cloudCredentialModelService = cloudCredentialModelService;
        this.computeServices = ImmutableSet.of();
    }

    private synchronized void update() {
        this.computeServices = ImmutableSet.copyOf(Iterables
            .transform(cloudCredentialModelService.getAll(),
                new Function<CloudCredential, ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>>() {
                    @Nullable @Override
                    public ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> apply(
                        CloudCredential cloudCredential) {
                        return computeServiceFactory.computeService(cloudCredential);
                    }
                }));
    }

    @Override
    public Iterator<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> iterator() {
        update();
        return ImmutableSet.copyOf(computeServices).iterator();
    }
}
