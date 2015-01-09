package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by daniel on 07.01.15.
 */
@Entity
public class Communication extends Model {

    @ManyToOne
    private ApplicationComponent provider;
    @ManyToOne
    private ApplicationComponent consumer;
    @OneToMany(mappedBy = "communication")
    private List<CommunicationChannel> communicationChannels;

    /**
     * No-args constructor for hibernate.
     */
    public Communication() {
    }

    public ApplicationComponent getProvider() {
        return provider;
    }

    public void setProvider(ApplicationComponent provider) {
        this.provider = provider;
    }

    public ApplicationComponent getConsumer() {
        return consumer;
    }

    public void setConsumer(ApplicationComponent consumer) {
        this.consumer = consumer;
    }
}
