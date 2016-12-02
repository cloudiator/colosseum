package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PlatformRuntimeDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformRuntime;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformRuntimeController extends GenericApiController<PlatformRuntime, PlatformRuntimeDto, PlatformRuntimeDto, PlatformRuntimeDto> {

    @Inject
    public PlatformRuntimeController(FrontendUserService frontendUserService,
                                      ModelService<Tenant> tenantModelService, ModelService<PlatformRuntime> modelService,
                                      TypeLiteral<PlatformRuntime> typeLiteral, ModelDtoConversionService conversionService){
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformRuntimeController.get(id).absoluteURL(request());
    }
}
