package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.CloudLocationDto;
import dtos.convert.api.ModelDtoConversionService;
import models.CloudLocation;
import models.service.api.CloudLocationServiceInterface;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class CloudLocationController extends GenericApiController<CloudLocation, CloudLocationDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param cloudLocationService the model service for retrieving the models.
     * @param conversionService    the conversion service for converting models and dtos.
     */
    @Inject
    protected CloudLocationController(CloudLocationServiceInterface cloudLocationService, ModelDtoConversionService conversionService) {
        super(cloudLocationService, conversionService);
    }


    @Override
    protected String getSelfRoute(Long id) {
        return routes.CloudLocationController.get(id).absoluteURL(request());
    }
}
