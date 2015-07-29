package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.KeyPairDto;
import dtos.conversion.ModelDtoConversionService;
import models.KeyPair;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by daniel on 19.05.15.
 */
public class KeyPairController
    extends GenericApiController<KeyPair, KeyPairDto, KeyPairDto, KeyPairDto> {

    @Inject public KeyPairController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<KeyPair> modelService,
        TypeLiteral<KeyPair> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.KeyPairController.get(id).absoluteURL(request());
    }
}
