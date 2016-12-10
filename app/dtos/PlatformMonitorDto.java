package dtos;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dtos.generic.MonitorDto;
import dtos.validation.validators.ModelIdValidator;
import models.*;
import models.service.ModelService;

/**
 * Created by frankgriesinger on 10.12.2016.
 */
public class PlatformMonitorDto extends MonitorDto {
    //private Long application;
    //private Long cloud;
    private Long applicationComponent;
    private Long platformComponent;
    private Long platformInstance;
    private Long sensorDescription;
    private Long schedule;
    private Long sensorConfigurations;


    public PlatformMonitorDto() {
        super();
    }

    @Override public void validation() {
        super.validation();

        validator(Long.class).validate(applicationComponent)
                .withValidator(new ModelIdValidator<>(PlatformMonitorDto.References.applicationComponentService.get()));
        validator(Long.class).validate(platformComponent)
                .withValidator(new ModelIdValidator<>(PlatformMonitorDto.References.platformComponentService.get()));
        validator(Long.class).validate(platformInstance)
                .withValidator(new ModelIdValidator<>(PlatformMonitorDto.References.platformInstanceService.get()));
        validator(Long.class).validate(sensorDescription)
                .withValidator(new ModelIdValidator<>(PlatformMonitorDto.References.sensorDescriptionService.get()));
        validator(Long.class).validate(schedule)
                .withValidator(new ModelIdValidator<>(PlatformMonitorDto.References.scheduleService.get()));
        validator(Long.class).validate(sensorConfigurations, "sensorConfigurations")
                .withValidator(new ModelIdValidator<>(PlatformMonitorDto.References.sensorConfigurationsService.get()));
    }

    public static class References {
        //@Inject
        //public static Provider<ModelService<Application>> applicationAddressService;
        @Inject public static Provider<ModelService<ApplicationComponent>> applicationComponentService;
        @Inject public static Provider<ModelService<PlatformComponent>> platformComponentService;
        @Inject public static Provider<ModelService<PlatformInstance>> platformInstanceService;
        @Inject public static Provider<ModelService<Cloud>> cloudService;
        @Inject public static Provider<ModelService<SensorDescription>> sensorDescriptionService;
        @Inject public static Provider<ModelService<Schedule>> scheduleService;
        @Inject public static Provider<ModelService<SensorConfigurations>> sensorConfigurationsService;
    }

    public Long getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(Long applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public Long getPlatformComponent() {
        return platformComponent;
    }

    public void setPlatformComponent(Long platformComponent) {
        this.platformComponent = platformComponent;
    }

    public Long getPlatformInstance() {
        return platformInstance;
    }

    public void setPlatformInstance(Long platformInstance) {
        this.platformInstance = platformInstance;
    }

    public Long getSensorDescription() {
        return sensorDescription;
    }

    public void setSensorDescription(Long sensorDescription) {
        this.sensorDescription = sensorDescription;
    }

    public Long getSchedule() {
        return schedule;
    }

    public void setSchedule(Long schedule) {
        this.schedule = schedule;
    }

    public Long getSensorConfigurations() {
        return sensorConfigurations;
    }

    public void setSensorConfigurations(Long sensorConfigurations) {
        this.sensorConfigurations = sensorConfigurations;
    }
}
