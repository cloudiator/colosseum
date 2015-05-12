package controllers;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.HardwareDto;
import dtos.conversion.api.ModelDtoConversionService;
import models.CloudCredential;
import models.FrontendGroup;
import models.FrontendUser;
import models.Hardware;
import models.service.api.FrontendUserService;
import models.service.api.generic.ModelService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 09.04.15.
 */
public class HardwareController
    extends GenericApiController<Hardware, HardwareDto, HardwareDto, HardwareDto> {

    private final FrontendUserService frontendUserModelService;

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService        the model service for retrieving the models.
     * @param typeLiteral         a type literal for the model type
     * @param conversionService   the conversion service for converting models and dtos.
     * @param frontendUserService the service to retrieve frontend users.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public HardwareController(ModelService<Hardware> modelService,
        TypeLiteral<Hardware> typeLiteral, ModelDtoConversionService conversionService,
        FrontendUserService frontendUserService) {
        super(modelService, typeLiteral, conversionService);
        checkNotNull(frontendUserService);
        this.frontendUserModelService = frontendUserService;
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.HardwareController.get(id).absoluteURL(request());
    }

    @Override protected Optional<Predicate<Hardware>> filter() {
        return Optional.of(new Predicate<Hardware>() {
            @Override public boolean apply(Hardware hardware) {
                String userName = request().username();
                FrontendUser frontendUser = frontendUserModelService.getByMail(userName);
                for (FrontendGroup frontendGroup : frontendUser.getFrontendGroups()) {
                    for (CloudCredential cloudCredential : frontendGroup.getCloudCredentials()) {
                        if (hardware.getCloudCredentials().contains(cloudCredential)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }
}
