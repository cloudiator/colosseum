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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by daniel on 19.06.15.
 */
public class BaseComputeServiceRegistry implements ComputeServiceRegistry {

    private final ComputeServiceFactory computeServiceFactory;
    private final ModelService<CloudCredential> cloudCredentialModelService;

    BaseComputeServiceRegistry(ComputeServiceFactory computeServiceFactory,
        ModelService<CloudCredential> cloudCredentialModelService) {
        this.computeServiceFactory = computeServiceFactory;
        this.cloudCredentialModelService = cloudCredentialModelService;
    }

    @Override
    public Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> getComputeServices() {
        return getComputeServices(cloudCredentialModelService.getAll());
    }

    @Override
    public ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation> getComputeService(
        String cloudCredentialUuid) {
        Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>>
            computeServices = getComputeServices(
            Collections.singletonList(cloudCredentialModelService.getByUuid(cloudCredentialUuid)));
        checkState(computeServices.size() == 1);
        return computeServices.iterator().next();
    }

    @Override
    public Set<ComputeService<HardwareInLocation, ImageInLocation, LocationInCloud, VirtualMachineInLocation>> getComputeServices(
        List<CloudCredential> cloudCredentials) {
        return ImmutableSet.copyOf(Iterables.transform(cloudCredentials,
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
        return ImmutableSet.copyOf(getComputeServices()).iterator();
    }
}
