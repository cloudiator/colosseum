package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.Cloud;
import models.Tenant;
import models.service.ModelService;

/**
 * Created by daniel on 19.05.15.
 */
public class KeyPairDto extends ValidatableDto {

    private Long cloud;
    private Long frontendGroup;
    private String privateKey;
    private String publicKey;
    private String cloudUuid;

    @Override public void validation() {
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(frontendGroup).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.frontendGroupService.get()));
        //TODO: validate if valid ssh keys?
        //TODO: validate with respect to public key or remoteId needs to be present?
    }

    public static class References {
        @Inject public static Provider<ModelService<Cloud>> cloudService;
        @Inject public static Provider<ModelService<Tenant>> frontendGroupService;
    }

    public Long getCloud() {
        return cloud;
    }

    public void setCloud(Long cloud) {
        this.cloud = cloud;
    }

    public Long getFrontendGroup() {
        return frontendGroup;
    }

    public void setFrontendGroup(Long frontendGroup) {
        this.frontendGroup = frontendGroup;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(String cloudUuid) {
        this.cloudUuid = cloudUuid;
    }
}
