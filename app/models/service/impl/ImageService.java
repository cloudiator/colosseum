package models.service.impl;

import com.google.inject.Inject;
import models.Image;
import models.repository.api.ImageRepository;
import models.service.api.ImageServiceInterface;
import models.service.impl.generic.ModelService;
import models.service.impl.generic.NamedModelService;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class ImageService extends NamedModelService<Image> implements ImageServiceInterface {

    @Inject
    public ImageService(ImageRepository imageRepository){super(imageRepository);}
}
