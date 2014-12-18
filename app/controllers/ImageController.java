package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.ImageDto;
import dtos.convert.api.ModelDtoConversionService;
import models.Image;
import models.service.api.ImageServiceInterface;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class ImageController extends GenericApiController<Image, ImageDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param imageService      the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected ImageController(ImageServiceInterface imageService, ModelDtoConversionService conversionService) {
        super(imageService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.ImageController.get(id).absoluteURL(request());
    }
}
