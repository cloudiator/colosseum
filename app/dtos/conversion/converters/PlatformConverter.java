package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.PlatformDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Platform;
import models.PlatformApi;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
@Singleton public class PlatformConverter extends AbstractConverter<Platform, PlatformDto> {

    private final ModelService<PlatformApi> platformApiModelService;

    @Inject
    protected PlatformConverter(ModelService<PlatformApi> platformApiModelService) {
        super(Platform.class, PlatformDto.class);
        this.platformApiModelService = platformApiModelService;
    }

    @Override
    public void configure() {
        binding().fromField("name").toField("name");
        binding().fromField("endpoint").toField("endpoint");
        binding(Long.class, PlatformApi.class).fromField("platformApi").toField("platformApi")
                .withTransformation(new IdToModelTransformer<>(platformApiModelService));
    }
}
