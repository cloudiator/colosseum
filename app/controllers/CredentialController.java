package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.CredentialDto;
import dtos.convert.api.ModelDtoConversionService;
import models.Credential;
import models.service.api.CredentialServiceInterface;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class CredentialController extends GenericApiController<Credential, CredentialDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param credentialService the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected CredentialController(CredentialServiceInterface credentialService, ModelDtoConversionService conversionService) {
        super(credentialService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.CredentialController.get(id).absoluteURL(request());
    }
}
