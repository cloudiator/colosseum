package models;

import models.generic.Model;
import models.generic.RemoteState;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Daniel Seybold on 24.11.2016.
 */
@Entity public class PlatformInstance extends Model {

    private RemoteState remoteState = RemoteState.INPROGRESS;

    @ManyToOne(optional = false) private ApplicationComponent applicationComponent;
    @ManyToOne(optional = false) private ApplicationInstance applicationInstance;

    @ManyToOne private PlatformEnvironment platformEnvironment;
    @Nullable
    @ManyToOne private Tenant tenant;

    /**
     * Empty constructor for hibernate.
     */
    protected PlatformInstance(){

    }

    public PlatformInstance(ApplicationComponent applicationComponent, ApplicationInstance applicationInstance, PlatformEnvironment platformEnvironment, Tenant tenant) {

        checkNotNull(applicationComponent);
        checkNotNull(applicationInstance);
        checkNotNull(platformEnvironment);
        checkNotNull(tenant);

        this.applicationComponent = applicationComponent;
        this.applicationInstance = applicationInstance;
        this.platformEnvironment = platformEnvironment;
        this.tenant = tenant;
    }

    public RemoteState getRemoteState() {
        if (remoteState == null) {
            return RemoteState.INPROGRESS;
        }
        return remoteState;
    }

    public void setRemoteState(RemoteState remoteState) {
        this.remoteState = remoteState;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public ApplicationInstance getApplicationInstance() {
        return applicationInstance;
    }

    public void setApplicationInstance(ApplicationInstance applicationInstance) {
        this.applicationInstance = applicationInstance;
    }

    public PlatformEnvironment getPlatformEnvironment() {
        return platformEnvironment;
    }

    public void setPlatformEnvironment(PlatformEnvironment platformEnvironment) {
        this.platformEnvironment = platformEnvironment;
    }

    @Nullable
    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(@Nullable Tenant tenant) {
        this.tenant = tenant;
    }
}
