package models.repository.api;

import com.google.inject.ImplementedBy;
import models.VirtualMachine;
import models.repository.api.generic.NamedModelRepository;
import models.repository.impl.VirtualMachineRepositoryJpa;

/**
 * Created by bwpc on 09.12.2014.
 */
@ImplementedBy(VirtualMachineRepositoryJpa.class)
public interface VirtualMachineRepository extends NamedModelRepository<VirtualMachine> {
}
