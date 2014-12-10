package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.HardwareDto;
import dtos.convert.api.ModelDtoConversionService;
import models.Hardware;
import models.service.api.HardwareServiceInterface;

/**
 * Created by daniel seybold on 10.12.2014.
 */
public class HardwareController extends GenericApiController<Hardware, HardwareDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param hardwareService      the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected HardwareController(HardwareServiceInterface hardwareService, ModelDtoConversionService conversionService) {
        super(hardwareService, conversionService);
    }
}
