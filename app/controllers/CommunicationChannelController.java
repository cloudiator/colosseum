package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.CommunicationChannelDto;
import dtos.convert.api.ModelDtoConversionService;
import models.CommunicationChannel;
import models.service.api.CommunicationChannelServiceInterface;

/**
 * Created by daniel on 09.01.15.
 */
public class CommunicationChannelController extends GenericApiController<CommunicationChannel, CommunicationChannelDto> {

    @Inject
    protected CommunicationChannelController(CommunicationChannelServiceInterface communicationChannelService, ModelDtoConversionService conversionService) {
        super(communicationChannelService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.CommunicationChannelController.get(id).absoluteURL(request());
    }
}
