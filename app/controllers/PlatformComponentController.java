package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

import controllers.generic.GenericApiController;
import dtos.PlatformComponentDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformComponent;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by Frank on 05.12.2016.
 */
public class PlatformComponentController extends
        GenericApiController<PlatformComponent, PlatformComponentDto, PlatformComponentDto, PlatformComponentDto> {

    @Inject
    public PlatformComponentController(FrontendUserService frontendUserService,
                                       ModelService<Tenant> tenantModelService, ModelService<PlatformComponent> modelService,
                                       TypeLiteral<PlatformComponent> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformComponentController.get(id).absoluteURL(request());
    }
}
