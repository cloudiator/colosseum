package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.CloudImageDto;
import dtos.convert.api.ModelDtoConversionService;
import models.CloudImage;
import models.service.api.CloudImageServiceInterface;
import models.service.api.generic.ModelServiceInterface;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class CloudImageController extends GenericApiController<CloudImage, CloudImageDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param cloudImageService      the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected CloudImageController(CloudImageServiceInterface cloudImageService, ModelDtoConversionService conversionService) {
        super(cloudImageService, conversionService);
    }
}
