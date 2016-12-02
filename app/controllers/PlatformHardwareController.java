package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PlatformHardwareDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformHardware;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformHardwareController extends GenericApiController<PlatformHardware, PlatformHardwareDto, PlatformHardwareDto, PlatformHardwareDto> {

    @Inject public PlatformHardwareController(FrontendUserService frontendUserService,
                                              ModelService<Tenant> tenantModelService, ModelService<PlatformHardware> modelService,
                                              TypeLiteral<PlatformHardware> typeLiteral, ModelDtoConversionService conversionService){
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformHardwareController.get(id).absoluteURL(request());
    }
}
