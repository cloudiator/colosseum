package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.ValidatableDto;
import dtos.validation.validators.ModelIdValidator;
import dtos.validation.validators.NotNullOrEmptyValidator;
import dtos.validation.validators.NotNullValidator;
import models.Platform;
import models.PlatformHardware;
import models.PlatformRuntime;
import models.service.ModelService;

/**
 * Created by Daniel Seybold on 28.11.2016.
 */
public class PlatformEnvironmentDto extends ValidatableDto {

    private String name;
    private Long platform;
    private Long platformHardware;
    private Long platformRuntime;


    public PlatformEnvironmentDto(){
        super();
    }

    @Override
    public void validation() {
        validator(String.class).validate(this.name).withValidator(new NotNullOrEmptyValidator());
        validator(Long.class).validate(platform).withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(PlatformEnvironmentDto.References.platformService.get()));
        validator(Long.class).validate(platformHardware).withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(PlatformEnvironmentDto.References.platformHardwareService.get()));
        validator(Long.class).validate(platformRuntime).withValidator(new NotNullValidator())
                .withValidator(new ModelIdValidator<>(PlatformEnvironmentDto.References.platformRuntimeService.get()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlatform() {
        return platform;
    }

    public void setPlatform(Long platform) {
        this.platform = platform;
    }

    public Long getPlatformHardware() {
        return platformHardware;
    }

    public void setPlatformHardware(Long platformHardware) {
        this.platformHardware = platformHardware;
    }

    public Long getPlatformRuntime() {
        return platformRuntime;
    }

    public void setPlatformRuntime(Long platformRuntime) {
        this.platformRuntime = platformRuntime;
    }

    public static class References {

        @Inject
        private static Provider<ModelService<Platform>> platformService;
        @Inject private static Provider<ModelService<PlatformHardware>> platformHardwareService;
        @Inject private static Provider<ModelService<PlatformRuntime>> platformRuntimeService;


        private References() {
        }
    }
}
