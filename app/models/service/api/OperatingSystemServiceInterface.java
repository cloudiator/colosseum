package models.service.api;

import com.google.inject.ImplementedBy;
import models.OperatingSystem;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.OperatingSystemService;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(OperatingSystemService.class)
public interface OperatingSystemServiceInterface extends ModelServiceInterface<OperatingSystem> {
}
