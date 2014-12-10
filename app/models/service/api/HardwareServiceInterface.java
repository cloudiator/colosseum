package models.service.api;

import com.google.inject.ImplementedBy;
import models.Hardware;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.HardwareService;

/**
 * Created by daniel seybold on 09.12.2014.
 */
@ImplementedBy(HardwareService.class)
public interface HardwareServiceInterface extends ModelServiceInterface<Hardware> {
}
