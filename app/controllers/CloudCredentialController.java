package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.CloudCredentialDto;
import dtos.conversion.ModelDtoConversionService;
import models.CloudCredential;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class CloudCredentialController extends
    GenericApiController<CloudCredential, CloudCredentialDto, CloudCredentialDto, CloudCredentialDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public CloudCredentialController(ModelService<CloudCredential> modelService,
        TypeLiteral<CloudCredential> typeLiteral, ModelDtoConversionService conversionService) {
        super(modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.CloudCredentialController.get(id).absoluteURL(request());
    }
}
