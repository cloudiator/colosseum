package models.generic;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Created by daniel on 08.01.15.
 */
@MappedSuperclass
public abstract class Resource {

    @Column(unique = true, nullable = false, updatable = false)
    private final String uuid = UUID.randomUUID().toString();

    /**
     * No-arg constructor for hibernate
     */
    protected Resource() {
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Resource)) {
            return false;
        }

        Resource resource = (Resource) o;

        return this.getUuid().equals(resource.getUuid());

    }

    @Override
    public int hashCode() {
        return this.getUuid().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Resource{uuid='%s'}", uuid);
    }
}
