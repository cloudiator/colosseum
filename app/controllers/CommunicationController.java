package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.CommunicationDto;
import dtos.convert.api.ModelDtoConversionService;
import models.Communication;
import models.service.api.CommunicationServiceInterface;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationController extends GenericApiController<Communication, CommunicationDto> {

    @Inject
    protected CommunicationController(CommunicationServiceInterface communicationService, ModelDtoConversionService conversionService) {
        super(communicationService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.CommunicationController.get(id).absoluteURL(request());
    }
}
