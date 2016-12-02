package dtos.conversion.converters;

import com.google.inject.Inject;
import dtos.PlatformInstanceDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.*;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformInstanceConverter extends AbstractConverter<PlatformInstance, PlatformInstanceDto> {

    private final ModelService<PlatformEnvironment> platformEnvironmentModelService;
    private final ModelService<ApplicationComponent> applicationComponentModelService;
    private final ModelService<ApplicationInstance> applicationInstanceModelService;

    @Inject protected PlatformInstanceConverter(ModelService<PlatformEnvironment> platformEnvironmentModelService,
                                                ModelService<ApplicationComponent> applicationComponentModelService,
                                                ModelService<ApplicationInstance> applicationInstanceModelService){

        super(PlatformInstance.class, PlatformInstanceDto.class);
        this.platformEnvironmentModelService = platformEnvironmentModelService;
        this.applicationComponentModelService = applicationComponentModelService;
        this.applicationInstanceModelService = applicationInstanceModelService;

    }

    @Override
    public void configure() {
        binding(Long.class, ApplicationComponent.class).fromField("applicationComponent")
                .toField("applicationComponent")
                .withTransformation(new IdToModelTransformer<>(applicationComponentModelService));
        binding(Long.class, ApplicationInstance.class).fromField("applicationInstance")
                .toField("applicationInstance")
                .withTransformation(new IdToModelTransformer<>(applicationInstanceModelService));
        binding(Long.class, PlatformEnvironment.class).fromField("platformEnvironment")
                .toField("platformEnvironment")
                .withTransformation(new IdToModelTransformer<>(platformEnvironmentModelService));
    }
}
