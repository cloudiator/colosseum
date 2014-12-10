package models.service.impl;

import com.google.inject.Inject;
import models.OperatingSystem;
import models.repository.api.OperatingSystemRepository;
import models.service.api.OperatingSystemServiceInterface;
import models.service.impl.generic.ModelService;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class OperatingSystemService extends ModelService<OperatingSystem> implements OperatingSystemServiceInterface {

    @Inject
    public OperatingSystemService(OperatingSystemRepository operatingSystemRepository){super(operatingSystemRepository);}
}
