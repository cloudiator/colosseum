package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PlatformDto;
import dtos.conversion.ModelDtoConversionService;
import models.Platform;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
public class PlatformController extends GenericApiController<Platform, PlatformDto, PlatformDto, PlatformDto> {

    @Inject
    public PlatformController(FrontendUserService frontendUserService,
                           ModelService<Tenant> tenantModelService, ModelService<Platform> modelService, TypeLiteral<Platform> typeLiteral,
                           ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformController.get(id).absoluteURL(request());
    }
}
