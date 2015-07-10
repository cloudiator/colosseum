package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.CloudCredentialDto;
import dtos.conversion.ModelDtoConversionService;
import models.CloudCredential;
import models.Tenant;
import models.service.api.FrontendUserService;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class CloudCredentialController extends
    GenericApiController<CloudCredential, CloudCredentialDto, CloudCredentialDto, CloudCredentialDto> {

    @Inject public CloudCredentialController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<CloudCredential> modelService,
        TypeLiteral<CloudCredential> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.CloudCredentialController.get(id).absoluteURL(request());
    }
}
