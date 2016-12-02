package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.PlatformCredentialDto;
import dtos.conversion.ModelDtoConversionService;
import models.PlatformCredential;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformCredentialController extends GenericApiController<PlatformCredential, PlatformCredentialDto, PlatformCredentialDto, PlatformCredentialDto> {

    @Inject
    public PlatformCredentialController(FrontendUserService frontendUserService,
                                     ModelService<Tenant> tenantModelService, ModelService<PlatformCredential> modelService,
                                     TypeLiteral<PlatformCredential> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
                conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.PlatformCredentialController.get(id).absoluteURL(request());
    }
}
