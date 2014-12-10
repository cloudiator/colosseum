package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Hardware;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.HardwareRepositoryJpa;

/**
 * Created by daniel seybold on 09.12.2014.
 */
@ImplementedBy(HardwareRepositoryJpa.class)
public interface HardwareRepository extends ModelRepository<Hardware> {
}
