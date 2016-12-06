package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.Platform;
import models.Tenant;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformCredentialDto extends ValidatableDto {

    private String user;
    private String secret;
    private Long platform;
    private Long tenant;

    public PlatformCredentialDto() {
        super();
    }

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

    public Long getPlatform() {
        return platform;
    }

    public void setPlatform(Long platform) {
        this.platform = platform;
    }

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }

    @Override
    public void validation() {
        validator(String.class).validate(user, "user").withValidator(new NotNullOrEmptyValidator());
        validator(String.class).validate(secret, "secret")
                .withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(platform, "platform").withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(PlatformCredentialDto.References.platformService.get()));
        validator(Long.class).validate(tenant, "tenant").withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(PlatformCredentialDto.References.frontendGroupService.get()));

    }

    public static class References {

        @Inject private static Provider<ModelService<Platform>> platformService;
        @Inject private static Provider<ModelService<Tenant>> frontendGroupService;

        private References() {
        }
    }
}
