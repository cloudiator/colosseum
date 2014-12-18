package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.VirtualMachineDto;
import dtos.convert.api.ModelDtoConversionService;
import models.VirtualMachine;
import models.service.api.VirtualMachineServiceInterface;

/**
 * Created by bwpc on 09.12.2014.
 */
public class VirtualMachineController extends GenericApiController<VirtualMachine, VirtualMachineDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param virtualMachineService the model service for retrieving the models.
     * @param conversionService     the conversion service for converting models and dtos.
     */
    @Inject
    protected VirtualMachineController(VirtualMachineServiceInterface virtualMachineService, ModelDtoConversionService conversionService) {
        super(virtualMachineService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.VirtualMachineController.get(id).absoluteURL(request());
    }
}
