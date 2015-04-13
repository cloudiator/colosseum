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
    private Long frontendUserGroup;

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

    public Long getFrontendUserGroup() {
        return frontendUserGroup;
    }

    public void setFrontendUserGroup(Long frontendUserGroup) {
        this.frontendUserGroup = frontendUserGroup;
    }

    @Override public void validation() {
        validator(String.class).validate(user).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(secret).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(frontendUserGroup).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.frontendGroupService.get()));
    }

    public static class References {

        @Inject public static Provider<ModelService<Cloud>> cloudService;

        @Inject public static Provider<ModelService<FrontendGroup>> frontendGroupService;
    }
}
