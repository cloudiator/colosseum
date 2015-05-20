package controllers;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import controllers.generic.GenericApiController;
import dtos.KeyPairDto;
import dtos.conversion.api.ModelDtoConversionService;
import models.KeyPair;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 19.05.15.
 */
public class KeyPairController
    extends GenericApiController<KeyPair, KeyPairDto, KeyPairDto, KeyPairDto> {

    /**
     * Constructs a GenericApiController.
     *
     * @param modelService      the model service for retrieving the models.
     * @param typeLiteral       a type literal for the model type
     * @param conversionService the conversion service for converting models and dtos.
     * @throws NullPointerException if any of the above parameters is null.
     */
    @Inject public KeyPairController(ModelService<KeyPair> modelService,
        TypeLiteral<KeyPair> typeLiteral, ModelDtoConversionService conversionService) {
        super(modelService, typeLiteral, conversionService);
    }

    @Override protected String getSelfRoute(Long id) {
        return controllers.routes.KeyPairController.get(id).absoluteURL(request());
    }
}
