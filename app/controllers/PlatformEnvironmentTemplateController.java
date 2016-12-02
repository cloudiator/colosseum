package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PlatformEnvironmentTemplateDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformEnvironmentTemplate;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformEnvironmentTemplateController extends GenericApiController<PlatformEnvironmentTemplate, PlatformEnvironmentTemplateDto, PlatformEnvironmentTemplateDto, PlatformEnvironmentTemplateDto> {

    @Inject
    public PlatformEnvironmentTemplateController(FrontendUserService frontendUserService,
                                         ModelService<Tenant> tenantModelService, ModelService<PlatformEnvironmentTemplate> modelService,
                                         TypeLiteral<PlatformEnvironmentTemplate> typeLiteral, ModelDtoConversionService conversionService
                                         ){
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);

    }

    @Override protected String getSelfRoute(Long id) {
        return routes.PlatformEnvironmentController.get(id).absoluteURL(request());
    }
}
