package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullValidator;
import models.ApplicationComponent;
import models.ApplicationInstance;
import models.PlatformEnvironment;
import models.service.BaseModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformInstanceDto extends ValidatableDto {

    private Long applicationComponent;
    private Long applicationInstance;
    private Long platformEnvironment;
    private String endpoint;

    public PlatformInstanceDto(){
        super();
    }

    @Override
    public void validation() {
        validator(Long.class).validate(applicationComponent).withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(PlatformInstanceDto.References.applicationComponentService.get()));
        validator(Long.class).validate(applicationInstance).withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(PlatformInstanceDto.References.applicationInstanceService.get()));
        validator(Long.class).validate(platformEnvironment).withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(References.platformEnvironmentService.get()));
    }

    public PlatformInstanceDto(Long applicationComponent, Long applicationInstance, Long platformEnvironment, String endpoint) {
        this.applicationComponent = applicationComponent;
        this.applicationInstance = applicationInstance;
        this.platformEnvironment = platformEnvironment;
        this.endpoint = endpoint;
    }

    public Long getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(Long applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public Long getApplicationInstance() {
        return applicationInstance;
    }

    public void setApplicationInstance(Long applicationInstance) {
        this.applicationInstance = applicationInstance;
    }

    public Long getPlatformEnvironment() {
        return platformEnvironment;
    }

    public void setPlatformEnvironment(Long platformEnvironment) {
        this.platformEnvironment = platformEnvironment;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public static class References {
        @Inject
        public static Provider<BaseModelService<ApplicationComponent>>
                applicationComponentService;
        @Inject private static Provider<BaseModelService<PlatformEnvironment>> platformEnvironmentService;
        @Inject private static Provider<BaseModelService<ApplicationInstance>>
                applicationInstanceService;

        private References() {
        }
    }
}
