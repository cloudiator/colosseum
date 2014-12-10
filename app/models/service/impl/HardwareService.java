package models.service.impl;

import com.google.inject.Inject;
import models.Hardware;
import models.repository.api.HardwareRepository;
import models.service.api.HardwareServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel seybold on 09.12.2014.
 */
public class HardwareService extends ModelService<Hardware> implements HardwareServiceInterface {

    @Inject
    public HardwareService(HardwareRepository hardwareRepository){super(hardwareRepository);}
}
