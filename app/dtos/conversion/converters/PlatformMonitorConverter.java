package dtos.conversion.converters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import dtos.PlatformApiDto;
import dtos.PlatformMonitorDto;
import dtos.conversion.AbstractConverter;
import dtos.conversion.transformers.IdToModelTransformer;
import dtos.conversion.transformers.ModelToListIdTransformer;
import dtos.conversion.transformers.Transformer;
import dtos.generic.KeyValue;
import dtos.generic.KeyValues;
import models.*;
import models.service.ModelService;

import java.util.List;
import java.util.Map;

/**
 * Created by frankgriesinger on 10.12.2016.
 */
@Singleton
public class PlatformMonitorConverter extends AbstractConverter<PlatformMonitor, PlatformMonitorDto> {

    //private final ModelService<Application> applicationModelService;
    private final ModelService<ApplicationComponent> applicationComponentModelService;
    private final ModelService<PlatformComponent> platformComponentService;
    private final ModelService<PlatformInstance> platformInstanceModelService;
    //private final ModelService<Cloud> cloudModelService;
    private final ModelService<SensorDescription> sensorDescriptionModelService;
    private final ModelService<Schedule> scheduleModelService;
    private final ModelService<MonitorInstance> monitorInstanceModelService;
    private final ModelService<SensorConfigurations> sensorConfigurationsModelService;


    @Inject
    protected PlatformMonitorConverter(ModelService<ApplicationComponent> applicationComponentModelService,
                                       ModelService<PlatformComponent> platformComponentService,
                                       ModelService<PlatformInstance> platformInstanceModelService,
                                       ModelService<SensorDescription> sensorDescriptionModelService,
                                       ModelService<Schedule> scheduleModelService,
                                       ModelService<MonitorInstance> monitorInstanceModelService,
                                       ModelService<SensorConfigurations> sensorConfigurationsModelService) {
        super(PlatformMonitor.class, PlatformMonitorDto.class);
        this.applicationComponentModelService = applicationComponentModelService;
        this.platformComponentService = platformComponentService;
        this.platformInstanceModelService = platformInstanceModelService;
        this.sensorDescriptionModelService = sensorDescriptionModelService;
        this.scheduleModelService = scheduleModelService;
        this.monitorInstanceModelService = monitorInstanceModelService;
        this.sensorConfigurationsModelService = sensorConfigurationsModelService;
    }

    @Override public void configure() {
        binding(Long.class, ApplicationComponent.class).fromField("applicationComponent").toField("applicationComponent")
                .withTransformation(new IdToModelTransformer<>(applicationComponentModelService));
        binding(Long.class, PlatformComponent.class).fromField("platformComponent").toField("platformComponent")
                .withTransformation(new IdToModelTransformer<>(platformComponentService));
        binding(Long.class, PlatformInstance.class).fromField("platformInstance").toField("platformInstance")
                .withTransformation(new IdToModelTransformer<>(platformInstanceModelService));
        binding(Long.class, SensorDescription.class).fromField("sensorDescription")
                .toField("sensorDescription")
                .withTransformation(new IdToModelTransformer<>(sensorDescriptionModelService));
        binding(Long.class, Schedule.class).fromField("schedule").toField("schedule")
                .withTransformation(new IdToModelTransformer<>(scheduleModelService));
        binding(Long.class, SensorConfigurations.class).fromField("sensorConfigurations")
                .toField("sensorConfigurations")
                .withTransformation(new IdToModelTransformer<>(sensorConfigurationsModelService));

// TODO: a one-way convertion is needed here:
        binding(new TypeLiteral<List<Long>>() {
        }, new TypeLiteral<List<MonitorInstance>>() {
        }).fromField("monitorInstances").toField("monitorInstances")
                .withTransformation(
                        new ModelToListIdTransformer<>(new IdToModelTransformer<>(monitorInstanceModelService)));

        binding(new TypeLiteral<List<KeyValue>>() {
        }, new TypeLiteral<Map<String, String>>() {
        }).fromField("externalReferences").toField("externalReferences").withTransformation(
                new Transformer<List<KeyValue>, Map<String, String>>() {
                    @Override public Map<String, String> transform(List<KeyValue> tags) {
                        return KeyValues.to(tags);
                    }

                    @Override public List<KeyValue> transformReverse(
                            Map<String, String> stringStringMap) {
                        return KeyValues.of(stringStringMap);
                    }
                });
    }
}
