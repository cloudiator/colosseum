package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.ApplicationInstanceDto;
import dtos.conversion.ModelDtoConversionService;
import models.ApplicationInstance;
import models.Tenant;
import models.service.api.FrontendUserService;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 11.05.15.
 */
public class ApplicationInstanceController extends
    GenericApiController<ApplicationInstance, ApplicationInstanceDto, ApplicationInstanceDto, ApplicationInstanceDto> {


    @Inject public ApplicationInstanceController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<ApplicationInstance> modelService,
        TypeLiteral<ApplicationInstance> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.ApplicationInstanceController.get(id).absoluteURL(request());
    }
}
