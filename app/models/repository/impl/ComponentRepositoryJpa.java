package models.repository.impl;

import models.Component;
import models.repository.api.ComponentRepository;
import models.repository.impl.generic.NamedModelRepositoryJpa;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ComponentRepositoryJpa extends NamedModelRepositoryJpa<Component> implements ComponentRepository {
}
