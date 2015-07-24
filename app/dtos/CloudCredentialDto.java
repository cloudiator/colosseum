package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.Cloud;
import models.Tenant;
import models.service.ModelService;

/**
 * Created by daniel on 29.03.15.
 */
public class CloudCredentialDto extends ValidatableDto {

    private String user;
    private String secret;
    private Long cloud;
    private Long tenant;

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

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }

    @Override public void validation() {
        validator(String.class).validate(user).withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(secret).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(cloud).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.cloudService.get()));
        validator(Long.class).validate(tenant).withValidator(new NotNullValidator())
            .withValidator(new ModelIdValidator<>(References.frontendGroupService.get()));
    }

    public static class References {

        @Inject private static Provider<ModelService<Cloud>> cloudService;
        @Inject private static Provider<ModelService<Tenant>> frontendGroupService;

        private References() {
        }
    }
}
