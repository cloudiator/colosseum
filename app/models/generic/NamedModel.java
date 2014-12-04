package models.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Superclass for models having a name property.
 * <p>
 * Provides setters and getters for the name property.
 */
@MappedSuperclass
public abstract class NamedModel extends Model {

    /**
     * Default constructor for hibernate.
     */
    public NamedModel() {
    }

    /**
     * Constructor setting the name property.
     *
     * @param name the name.
     */
    public NamedModel(String name) {
        this.name = name;
    }

    @Column(unique = true, nullable = false)
    protected String name;

    @Override
    public String toString() {
        return name;
    }

    /**
     * Getter for the name.
     *
     * @return the value of the name property.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name.
     * <p>
     * The name must be unique for all stored
     * entities of this type.
     *
     * @param name the value of the name property.
     */
    public void setName(String name) {
        this.name = name;
    }
}
