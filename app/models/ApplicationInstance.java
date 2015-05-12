package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by daniel on 11.05.15.
 */
@Entity public class ApplicationInstance extends Model {

    @ManyToOne(optional = false) private Application application;
    @OneToMany(mappedBy = "applicationInstance") private List<Instance> instances;

    /**
     * No-args constructor for hibernate.
     */
    private ApplicationInstance() {

    }

    public ApplicationInstance(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }
}
