package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.PlatformEnvironmentTemplateDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Platform;
import models.PlatformEnvironmentTemplate;
import models.PlatformHardware;
import models.PlatformRuntime;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
@Singleton
public class PlatformEnvironmentTemplateConverter extends AbstractConverter<PlatformEnvironmentTemplate, PlatformEnvironmentTemplateDto> {


    private final ModelService<Platform> platformModelService;
    private final ModelService<PlatformHardware> platformHardwareModelService;
    private final ModelService<PlatformRuntime> platformRuntimeModelService;

    @Inject protected PlatformEnvironmentTemplateConverter(ModelService<Platform> platformModelService, ModelService<PlatformHardware> platformHardwareModelService, ModelService<PlatformRuntime> platformRuntimeModelService){

        super(PlatformEnvironmentTemplate.class, PlatformEnvironmentTemplateDto.class);
        this.platformModelService = platformModelService;
        this.platformHardwareModelService = platformHardwareModelService;
        this.platformRuntimeModelService = platformRuntimeModelService;

    }


    @Override
    public void configure() {
        binding(Long.class, Platform.class).fromField("platform").toField("platform")
                .withTransformation(new IdToModelTransformer<>(platformModelService));
        binding(Long.class, PlatformHardware.class).fromField("platformHardware").toField("platformHardware")
                .withTransformation(new IdToModelTransformer<>(platformHardwareModelService));
        binding(Long.class, PlatformRuntime.class).fromField("platformRuntime").toField("platformRuntime")
                .withTransformation(new IdToModelTransformer<>(platformRuntimeModelService));

    }
}




