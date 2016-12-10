package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.PlatformCredentialDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Platform;
import models.PlatformCredential;
import models.Tenant;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
@Singleton
public class PlatformCredentialConverter extends AbstractConverter<PlatformCredential, PlatformCredentialDto> {

    private final ModelService<Platform> platformModelService;
    private final ModelService<Tenant> frontendGroupModelService;

    @Inject
    protected PlatformCredentialConverter(ModelService<Platform> platformModelService,
                                       ModelService<Tenant> frontendGroupModelService) {
        super(PlatformCredential.class, PlatformCredentialDto.class);
        this.platformModelService = platformModelService;
        this.frontendGroupModelService = frontendGroupModelService;
    }

    @Override
    public void configure() {
        binding().fromField("user").toField("user");
        binding().fromField("secret").toField("secret");
        binding(Long.class, Platform.class).fromField("platform").toField("platform")
                .withTransformation(new IdToModelTransformer<>(platformModelService));
        binding(Long.class, Tenant.class).fromField("tenant").toField("tenant")
                .withTransformation(new IdToModelTransformer<>(frontendGroupModelService));

    }
}
