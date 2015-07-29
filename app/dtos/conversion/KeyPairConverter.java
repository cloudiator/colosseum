package dtos.conversion;

import com.google.inject.Inject;
import dtos.KeyPairDto;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Cloud;
import models.Tenant;
import models.KeyPair;
import models.service.ModelService;

/**
 * Created by daniel on 19.05.15.
 */
public class KeyPairConverter extends AbstractConverter<KeyPair, KeyPairDto> {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<Tenant> frontendGroupModelService;

    @Inject protected KeyPairConverter(ModelService<Cloud> cloudModelService,
        ModelService<Tenant> frontendGroupModelService) {
        super(KeyPair.class, KeyPairDto.class);
        this.cloudModelService = cloudModelService;
        this.frontendGroupModelService = frontendGroupModelService;
    }

    @Override public void configure() {
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        builder().from(Long.class, "frontendGroup").to(Tenant.class, "frontendGroup")
            .withTransformation(new IdToModelTransformer<>(frontendGroupModelService));
        builder().from("privateKey").to("privateKey");
        builder().from("publicKey").to("publicKey");
        builder().from("remoteId").to("remoteId");
    }
}
