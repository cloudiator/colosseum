package models.repository.api;

import com.google.inject.ImplementedBy;
import models.FrontendUser;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.FrontendUserRepositoryJpa;

/**
 * Created by daniel on 03.11.14.
 */
@ImplementedBy(FrontendUserRepositoryJpa.class)
public interface FrontendUserRepository extends ModelRepository<FrontendUser>{

    public FrontendUser findByMail(String mail);

}
