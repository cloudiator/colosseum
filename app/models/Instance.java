package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel on 12.12.14.
 */
@Entity
public class Instance extends Model {

    @ManyToOne
    private ApplicationComponent applicationComponent;

    @ManyToOne
    private VirtualMachine virtualMachine;

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        checkNotNull(applicationComponent);
        this.applicationComponent = applicationComponent;
    }

    public VirtualMachine getVirtualMachine() {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachine virtualMachine) {
        checkNotNull(virtualMachine);
        this.virtualMachine = virtualMachine;
    }
}
