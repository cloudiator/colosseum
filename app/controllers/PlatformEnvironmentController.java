package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PlatformEnvironmentDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformEnvironment;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformEnvironmentController extends GenericApiController<PlatformEnvironment, PlatformEnvironmentDto, PlatformEnvironmentDto, PlatformEnvironmentDto> {

    @Inject public PlatformEnvironmentController(FrontendUserService frontendUserService,
                                                 ModelService<Tenant> tenantModelService, ModelService<PlatformEnvironment> modelService,
                                                 TypeLiteral<PlatformEnvironment> typeLiteral, ModelDtoConversionService conversionService
                                                 ){
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);

    }

    @Override protected String getSelfRoute(Long id) {
        return routes.PlatformEnvironmentController.get(id).absoluteURL(request());
    }
}
