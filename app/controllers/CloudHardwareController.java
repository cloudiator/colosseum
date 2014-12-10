package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.CloudHardwareDto;
import dtos.convert.api.ModelDtoConversionService;
import models.CloudHardware;
import models.service.api.CloudHardwareFlavorServiceInterface;

/**
 * Created by daniel seybold on 09.12.2014.
 */
public class CloudHardwareController extends GenericApiController<CloudHardware, CloudHardwareDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param cloudHardwareFlavorService    the model service for retrieving the models.
     * @param conversionService             the conversion service for converting models and dtos.
     */
    @Inject
    protected CloudHardwareController(CloudHardwareFlavorServiceInterface cloudHardwareFlavorService, ModelDtoConversionService conversionService) {
        super(cloudHardwareFlavorService, conversionService);
    }
}
