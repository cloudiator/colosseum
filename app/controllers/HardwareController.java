package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.HardwareDto;
import dtos.conversion.api.ModelDtoConversionService;
import models.Hardware;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 09.04.15.
 */
public class HardwareController
    extends GenericApiController<Hardware, HardwareDto, HardwareDto, HardwareDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public HardwareController(ModelService<Hardware> modelService,
        TypeLiteral<Hardware> typeLiteral, ModelDtoConversionService conversionService) {
        super(modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.HardwareController.get(id).absoluteURL(request());
    }
}
