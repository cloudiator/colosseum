package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.FrontendUserDto;
import dtos.convert.api.ModelDtoConversionService;
import models.FrontendUser;
import models.service.api.FrontendUserServiceInterface;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class FrontendUserController extends GenericApiController<FrontendUser, FrontendUserDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param frontendUserService the model service for retrieving the models.
     * @param conversionService   the conversion service for converting models and dtos.
     */
    @Inject
    protected FrontendUserController(FrontendUserServiceInterface frontendUserService, ModelDtoConversionService conversionService) {
        super(frontendUserService, conversionService);
    }


    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.FrontendUserController.get(id).absoluteURL(request());
    }
}
