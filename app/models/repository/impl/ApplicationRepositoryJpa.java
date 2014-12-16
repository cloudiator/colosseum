package models.repository.impl;

import models.Application;
import models.repository.api.ApplicationRepository;
import models.repository.impl.generic.NamedModelRepositoryJpa;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ApplicationRepositoryJpa extends NamedModelRepositoryJpa<Application> implements ApplicationRepository {
}
