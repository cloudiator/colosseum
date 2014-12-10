package models.repository.api;

import com.google.inject.ImplementedBy;
import models.Image;
import models.repository.api.generic.ModelRepository;
import models.repository.impl.ImageRepositoryJpa;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(ImageRepositoryJpa.class)
public interface ImageRepository extends ModelRepository<Image> {
}
