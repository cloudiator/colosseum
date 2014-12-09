package models.service.impl;

import com.google.inject.Inject;
import models.VirtualMachine;
import models.repository.api.VirtualMachineRepository;
import models.service.api.VirtualMachineServiceInterface;
import models.service.impl.generic.NamedModelService;

/**
 * Created by bwpc on 09.12.2014.
 */
public class VirtualMachineService extends NamedModelService<VirtualMachine> implements VirtualMachineServiceInterface {

    @Inject
    public VirtualMachineService(VirtualMachineRepository virtualMachineRepository) {
        super(virtualMachineRepository);
    }
}
