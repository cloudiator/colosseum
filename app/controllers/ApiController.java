package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.ApiDto;
import dtos.convert.api.ModelDtoConversionService;
import models.Api;
import models.service.api.ApiServiceInterface;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class ApiController extends GenericApiController<Api, ApiDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param apiService      the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected ApiController(ApiServiceInterface apiService, ModelDtoConversionService conversionService) {
        super(apiService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.ApiController.get(id).absoluteURL(request());
    }
}
