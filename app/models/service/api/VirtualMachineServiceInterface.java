package models.service.api;

import com.google.inject.ImplementedBy;
import models.VirtualMachine;
import models.service.api.generic.NamedModelServiceInterface;
import models.service.impl.VirtualMachineService;

/**
 * Created by daniel seybold on 09.12.2014.
 */
@ImplementedBy(VirtualMachineService.class)
public interface VirtualMachineServiceInterface extends NamedModelServiceInterface<VirtualMachine> {
}
