package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
@Entity public class PlatformEnvironmentTemplate extends Model {

    /**
     * Owned relations
     */
    @ManyToOne(optional = false) private Platform platform;
    @ManyToOne(optional = false) private PlatformRuntime platformRuntime;
    @ManyToOne(optional = false) private PlatformHardware platformHardware;
    //@ManyToOne(optional = true) private PlatformService platformService;

    /**
     * Empty constructor for hibernate.
     */
    protected PlatformEnvironmentTemplate() {
    }

    public PlatformEnvironmentTemplate(Platform platform, PlatformRuntime platformRuntime, PlatformHardware platformHardware) {

        checkNotNull(platform);
        checkNotNull(platformRuntime);
        checkNotNull(platformHardware);


        this.platform = platform;
        this.platformRuntime = platformRuntime;
        this.platformHardware = platformHardware;
        //this.platformService = platformService;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public PlatformRuntime getPlatformRuntime() {
        return platformRuntime;
    }

    public void setPlatformRuntime(PlatformRuntime platformRuntime) {
        this.platformRuntime = platformRuntime;
    }

    public PlatformHardware getPlatformHardware() {
        return platformHardware;
    }

    public void setPlatformHardware(PlatformHardware platformHardware) {
        this.platformHardware = platformHardware;
    }

    /*
    public PlatformService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(PlatformService platformService) {
        this.platformService = platformService;
    }
    */
}
