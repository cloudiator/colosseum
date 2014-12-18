package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.ApplicationComponentDto;
import dtos.convert.api.ModelDtoConversionService;
import models.ApplicationComponent;
import models.service.api.ApplicationComponentServiceInterface;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class ApplicationComponentController extends GenericApiController<ApplicationComponent, ApplicationComponentDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param applicationComponentService the model service for retrieving the models.
     * @param conversionService           the conversion service for converting models and dtos.
     */
    @Inject
    protected ApplicationComponentController(ApplicationComponentServiceInterface applicationComponentService, ModelDtoConversionService conversionService) {
        super(applicationComponentService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.ApplicationComponentController.get(id).absoluteURL(request());
    }
}
