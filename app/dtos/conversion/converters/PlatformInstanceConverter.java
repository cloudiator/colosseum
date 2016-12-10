package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.PlatformInstanceDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.*;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
@Singleton
public class PlatformInstanceConverter extends RemoteConverter<PlatformInstance, PlatformInstanceDto> {

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
        //todo fix inheritance hierachy
        //super.configure();

        // todo workaround for incorrect inheritance
        // those two are copied from parent converter
        binding().fromField("remoteId").toField("remoteId");
        binding().fromField("remoteState").toField("remoteState");

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
