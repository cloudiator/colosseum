package models;

import models.Application;
import models.Component;
import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.inject.internal.util.$Preconditions.checkNotNull;

/**
 * Created by daniel on 12.12.14.
 */
@Entity
public class ApplicationComponent extends Model {

    @ManyToOne(optional = false)
    private Application application;

    @ManyToOne(optional = false)
    private Component component;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        checkNotNull(application);
        this.application = application;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        checkNotNull(component);
        this.component = component;
    }
}
