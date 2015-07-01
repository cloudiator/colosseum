package dtos.conversion.converters;

import com.google.inject.Inject;
import dtos.KeyPairDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import models.Cloud;
import models.FrontendGroup;
import models.KeyPair;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 19.05.15.
 */
public class KeyPairConverter extends AbstractConverter<KeyPair, KeyPairDto> {

    private final ModelService<Cloud> cloudModelService;
    private final ModelService<FrontendGroup> frontendGroupModelService;

    @Inject protected KeyPairConverter(ModelService<Cloud> cloudModelService,
        ModelService<FrontendGroup> frontendGroupModelService) {
        super(KeyPair.class, KeyPairDto.class);
        this.cloudModelService = cloudModelService;
        this.frontendGroupModelService = frontendGroupModelService;
    }

    @Override public void configure() {
        builder().from(Long.class, "cloud").to(Cloud.class, "cloud")
            .withTransformation(new IdToModelTransformer<>(cloudModelService));
        builder().from(Long.class, "frontendGroup").to(FrontendGroup.class, "frontendGroup")
            .withTransformation(new IdToModelTransformer<>(frontendGroupModelService));
        builder().from("privateKey").to("privateKey");
        builder().from("publicKey").to("publicKey");
        builder().from("remoteId").to("remoteId");
    }
}
