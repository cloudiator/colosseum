package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by daniel on 08.01.15.
 */
@Entity
public class CommunicationChannel extends Model {

    @ManyToOne
    private Communication communication;
    @ManyToOne
    private Instance provider;
    @ManyToOne
    private Instance consumer;

    public Communication getCommunication() {
        return communication;
    }

    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    public Instance getProvider() {
        return provider;
    }

    public void setProvider(Instance provider) {
        this.provider = provider;
    }

    public Instance getConsumer() {
        return consumer;
    }

    public void setConsumer(Instance consumer) {
        this.consumer = consumer;
    }
}
