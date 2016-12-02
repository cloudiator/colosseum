package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PlatformInstanceDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformInstance;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;


/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformInstanceController extends GenericApiController<PlatformInstance, PlatformInstanceDto, PlatformInstanceDto, PlatformInstanceDto> {

    @Inject
    public PlatformInstanceController(FrontendUserService frontendUserService,
                              ModelService<Tenant> tenantModelService, ModelService<PlatformInstance> modelService,
                              TypeLiteral<PlatformInstance> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformInstanceController.get(id).absoluteURL(request());
    }


}
