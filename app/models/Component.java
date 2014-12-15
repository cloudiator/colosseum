package models;

import models.generic.NamedModel;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by daniel on 12.12.14.
 */
@Entity
public class Component extends NamedModel{

    @OneToMany(mappedBy = "component")
    private List<ApplicationComponent> applicationComponents;

    public List<ApplicationComponent> getApplicationComponents() {
        return applicationComponents;
    }

    public void setApplicationComponents(List<ApplicationComponent> applicationComponents) {
        this.applicationComponents = applicationComponents;
    }
}
