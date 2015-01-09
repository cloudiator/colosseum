package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel on 12.12.14.
 */
@Entity
public class Instance extends Model {

    @ManyToOne
    private ApplicationComponent applicationComponent;

    @OneToMany(mappedBy = "provider")
    private List<CommunicationChannel> providedCommunicationChannels;
    @OneToMany(mappedBy = "consumer")
    private List<CommunicationChannel> consumedCommunicationChannels;

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

    public List<CommunicationChannel> getProvidedCommunicationChannels() {
        return providedCommunicationChannels;
    }

    public void setProvidedCommunicationChannels(List<CommunicationChannel> providedCommunicationChannels) {
        this.providedCommunicationChannels = providedCommunicationChannels;
    }

    public List<CommunicationChannel> getConsumedCommunicationChannels() {
        return consumedCommunicationChannels;
    }

    public void setConsumedCommunicationChannels(List<CommunicationChannel> consumedCommunicationChannels) {
        this.consumedCommunicationChannels = consumedCommunicationChannels;
    }
}
