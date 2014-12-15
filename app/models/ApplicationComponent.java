package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by daniel on 12.12.14.
 */
@Entity
public class ApplicationComponent extends Model {

    @ManyToOne
    private Application application;

    @ManyToOne
    private Component component;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
