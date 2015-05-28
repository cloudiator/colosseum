package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.ApplicationInstanceDto;
import dtos.conversion.ModelDtoConversionService;
import models.ApplicationInstance;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 11.05.15.
 */
public class ApplicationInstanceController extends
    GenericApiController<ApplicationInstance, ApplicationInstanceDto, ApplicationInstanceDto, ApplicationInstanceDto> {

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public ApplicationInstanceController(ModelService<ApplicationInstance> modelService,
        TypeLiteral<ApplicationInstance> typeLiteral, ModelDtoConversionService conversionService) {
        super(modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ApplicationInstanceController.get(id).absoluteURL(request());
    }
}
