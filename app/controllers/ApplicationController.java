package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.ApplicationDto;
import dtos.conversion.ModelDtoConversionService;
import models.Application;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class ApplicationController
    extends GenericApiController<Application, ApplicationDto, ApplicationDto, ApplicationDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public ApplicationController(ModelService<Application> modelService,
        TypeLiteral<Application> typeLiteral, ModelDtoConversionService conversionService) {
        super(modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ApplicationController.get(id).absoluteURL(request());
    }
}
