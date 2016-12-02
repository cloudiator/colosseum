package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PlatformApiDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformApi;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
public class PlatformApiController extends GenericApiController<PlatformApi, PlatformApiDto, PlatformApiDto, PlatformApiDto> {

    @Inject public PlatformApiController(FrontendUserService frontendUserService,
                                         models.service.ModelService<Tenant> tenantModelService, ModelService<PlatformApi> modelService,
                                         TypeLiteral<PlatformApi> typeLiteral, ModelDtoConversionService conversionService){
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformApiController.get(id).absoluteURL(request());
    }
}
