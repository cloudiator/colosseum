package models.service.api;

import com.google.inject.ImplementedBy;
import models.Image;
import models.service.api.generic.ModelServiceInterface;
import models.service.impl.ImageService;

/**
 * Created by daniel seybold on 10.12.2014.
 */
@ImplementedBy(ImageService.class)
public interface ImageServiceInterface extends ModelServiceInterface<Image> {
}
