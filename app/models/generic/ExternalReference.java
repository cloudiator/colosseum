package models.generic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by Frank on 20.05.2015.
 */
@Entity
public class ExternalReference extends Model {

    @Column(nullable = false, updatable = false) private String reference;
    @ManyToOne private ModelWithExternalReference modelWithExternalReference;

    /**
     * no-args constructor for hibernate.
     */
    protected ExternalReference() {
    }

    public ExternalReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
