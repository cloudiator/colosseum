package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.ApplicationDto;
import dtos.convert.api.ModelDtoConversionService;
import models.Application;
import models.service.api.ApplicationServiceInterface;
import models.service.api.generic.ModelServiceInterface;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class ApplicationController extends GenericApiController<Application, ApplicationDto> {


    /**
     * Constructs a GenericApiController.
     *
     * @param applicationService      the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected ApplicationController(ApplicationServiceInterface applicationService, ModelDtoConversionService conversionService) {
        super(applicationService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.ApplicationController.get(id).absoluteURL(request());
    }
}
