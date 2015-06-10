package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.ModelIdValidator;
import dtos.validation.NotNullOrEmptyValidator;
import dtos.validation.NotNullValidator;
import models.Cloud;
import models.FrontendGroup;
import models.service.api.generic.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class CloudCredentialDto extends ValidatableDto {

    private String user;
    private String secret;
    private Long cloud;
    private Long frontendGroup;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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

    @Override public void validation() {
        validator(String.class).validate(user).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(secret).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(frontendGroup).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.frontendGroupService.get()));
    }

    public static class References {

        private References() {
        }

        @Inject private static Provider<ModelService<Cloud>> cloudService;

        @Inject private static Provider<ModelService<FrontendGroup>> frontendGroupService;
    }
}
