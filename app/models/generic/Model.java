package models.generic;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Superclass for all model classes.
 * <p>
 * Defines the auto generated id for
 * each model class.
 */
@MappedSuperclass
public abstract class Model extends Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    /**
     * Default constructor for hibernate.
     */
    public Model() {
    }

    @Override
    public String toString() {
        if (id == null) {
            return super.toString();
        }
        return String.valueOf(id);
    }

    /**
     * Getter for the id.
     *
     * @return the identifies for this model object.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the id.
     *
     * @param id the identified for this model object
     */
    public void setId(Long id) {
        this.id = id;
    }
}
