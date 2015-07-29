package controllers;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.HardwareDto;
import dtos.conversion.ModelDtoConversionService;
import models.CloudCredential;
import models.Hardware;
import models.Tenant;
import models.service.FrontendUserService;
import models.service.ModelService;

/**
 * Created by daniel on 09.04.15.
 */
public class HardwareController
    extends GenericApiController<Hardware, HardwareDto, HardwareDto, HardwareDto> {

    @Inject public HardwareController(FrontendUserService frontendUserService,
        ModelService<Tenant> tenantModelService, ModelService<Hardware> modelService,
        TypeLiteral<Hardware> typeLiteral, ModelDtoConversionService conversionService) {
        super(frontendUserService, tenantModelService, modelService, typeLiteral,
            conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.HardwareController.get(id).absoluteURL(request());
    }

    @Override protected Optional<Predicate<Hardware>> filter() {
        return Optional.of(hardware -> {
            for (Tenant tenant : getUser().getTenants()) {
                for (CloudCredential cloudCredential : tenant.getCloudCredentials()) {
                    if (hardware.getCloudCredentials().contains(cloudCredential)) {
                        return true;
                    }
                }
            }
            return false;
        });
    }
}
