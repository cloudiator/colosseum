package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.ApplicationComponentDto;
import dtos.conversion.ModelDtoConversionService;
import models.ApplicationComponent;
import models.Tenant;
import models.service.api.FrontendUserService;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class ApplicationComponentController extends
    GenericApiController<ApplicationComponent, ApplicationComponentDto, ApplicationComponentDto, ApplicationComponentDto> {
    
    @Inject public ApplicationComponentController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<ApplicationComponent> modelService,
        TypeLiteral<ApplicationComponent> typeLiteral,
        ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ApplicationComponentController.get(id).absoluteURL(request());
    }
}
