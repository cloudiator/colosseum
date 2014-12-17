package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.InstanceDto;
import dtos.convert.api.ModelDtoConversionService;
import models.Instance;
import models.service.api.InstanceServiceInterface;

/**
 * Created by daniel seybold on 17.12.2014.
 */
public class InstanceController extends GenericApiController<Instance, InstanceDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param instanceService      the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected InstanceController(InstanceServiceInterface instanceService, ModelDtoConversionService conversionService) {
        super(instanceService, conversionService);
    }
}
