package controllers;

import com.google.inject.Inject;
import controllers.generic.GenericApiController;
import dtos.LifecycleComponentDto;
import dtos.convert.api.ModelDtoConversionService;
import models.LifecycleComponent;
import models.service.api.LifecycleComponentServiceInterface;

/**
 * Created by daniel seybold on 16.12.2014.
 */
public class LifecycleComponentController extends GenericApiController<LifecycleComponent, LifecycleComponentDto> {
    /**
     * Constructs a GenericApiController.
     *
     * @param lifecycleComponentService the model service for retrieving the models.
     * @param conversionService         the conversion service for converting models and dtos.
     */
    @Inject
    protected LifecycleComponentController(LifecycleComponentServiceInterface lifecycleComponentService, ModelDtoConversionService conversionService) {
        super(lifecycleComponentService, conversionService);
    }

    @Override
    protected String getSelfRoute(Long id) {
        return controllers.routes.LifecycleComponentController.get(id).absoluteURL(request());
    }
}
