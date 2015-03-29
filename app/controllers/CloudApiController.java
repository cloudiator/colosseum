package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.CloudApiDto;
import dtos.conversion.api.ModelDtoConversionService;
import models.CloudApi;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class CloudApiController
    extends GenericApiController<CloudApi, CloudApiDto, CloudApiDto, CloudApiDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public CloudApiController(ModelService<CloudApi> modelService,
        TypeLiteral<CloudApi> typeLiteral, ModelDtoConversionService conversionService) {
        super(modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.CloudApiController.get(id).absoluteURL(request());
    }
}
