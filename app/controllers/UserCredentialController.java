package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.UserCredentialDto;
import dtos.convert.api.ModelDtoConversionService;
import models.UserCredential;
import models.service.api.UserCredentialServiceInterface;
import models.service.api.generic.ModelServiceInterface;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class UserCredentialController extends GenericApiController<UserCredential, UserCredentialDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param userCredentialService the model service for retrieving the models.
     * @param conversionService     the conversion service for converting models and dtos.
     */
    @Inject
    protected UserCredentialController(UserCredentialServiceInterface userCredentialService, ModelDtoConversionService conversionService) {
        super(userCredentialService, conversionService);
    }
}
