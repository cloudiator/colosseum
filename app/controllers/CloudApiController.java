package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.CloudApiDto;
import dtos.convert.api.ModelDtoConversionService;
import models.CloudApi;
import models.service.api.CloudApiServiceInterface;
import models.service.api.generic.ModelServiceInterface;

/**
 * Created by daniel seybold on 11.12.2014.
 */
public class CloudApiController extends GenericApiController<CloudApi, CloudApiDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param cloudApiService      the model service for retrieving the models.
     * @param conversionService the conversion service for converting models and dtos.
     */
    @Inject
    protected CloudApiController(CloudApiServiceInterface cloudApiService, ModelDtoConversionService conversionService) {
        super(cloudApiService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.CloudApiController.get(id).absoluteURL(request());
    }
}
