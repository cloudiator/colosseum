package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.ApiDto;
import dtos.conversion.ModelDtoConversionService;
import models.Api;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class ApiController extends GenericApiController<Api, ApiDto, ApiDto, ApiDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public ApiController(ModelService<Api> modelService, TypeLiteral<Api> typeLiteral,
        ModelDtoConversionService conversionService) {
        super(modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ApiController.get(id).absoluteURL(request());
    }
}
