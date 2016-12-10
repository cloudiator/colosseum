package models;

import components.scalability.internal.TsdbLocator;

import javax.annotation.Nullable;
import javax.persistence.ManyToOne;
import java.util.Optional;

/**
 * Created by frankgriesinger on 09.12.2016.
 */
public class PlatformMonitor extends MetricMonitor {

    /* filter */
    //@ManyToOne(optional = true) private Application application;
    //@ManyToOne(optional = true) private Cloud cloud;
    @ManyToOne(optional = true) private ApplicationComponent applicationComponent; // error on non-platformcomponents
    @ManyToOne(optional = true) private PlatformComponent platformComponent;
    @ManyToOne(optional = true) private PlatformInstance platformInstance;
    @ManyToOne(optional = true) private SensorDescription sensorDescription;

    @Nullable
    @ManyToOne(optional = true) private SensorConfigurations sensorConfigurations;

    /**
     * Empty constructor for hibernate.
     */
    protected PlatformMonitor() {
    }

    public PlatformMonitor(Schedule schedule, ApplicationComponent applicationComponent, PlatformComponent platformComponent, PlatformInstance platformInstance, SensorDescription sensorDescription, SensorConfigurations sensorConfigurations) {
        super(schedule);
        this.applicationComponent = applicationComponent;
        this.platformComponent = platformComponent;
        this.platformInstance = platformInstance;
        this.sensorDescription = sensorDescription;
        this.sensorConfigurations = sensorConfigurations;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public PlatformComponent getPlatformComponent() {
        return platformComponent;
    }

    public PlatformInstance getPlatformInstance() {
        return platformInstance;
    }

    public SensorDescription getSensorDescription() {
        return sensorDescription;
    }

    @Override
    protected TsdbLocator getTsdbLocator() {
        return null; //TODO
    }

    @Nullable
    public Optional<SensorConfigurations> getSensorConfigurations() {
        return Optional.ofNullable(sensorConfigurations);
    }
}
