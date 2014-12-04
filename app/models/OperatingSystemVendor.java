package models;

import models.generic.NamedModel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by daniel on 04.11.14.
 */
@Entity
public class OperatingSystemVendor extends NamedModel {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "operatingSystemVendor")
    private List<OperatingSystem> operatingSystems;

    @Enumerated(EnumType.STRING)
    private OperatingSystemVendorType operatingSystemVendorType;

    public OperatingSystemVendorType getOperatingSystemVendorType() {
        return operatingSystemVendorType;
    }

    public void setOperatingSystemVendorType(OperatingSystemVendorType operatingSystemVendorType) {
        this.operatingSystemVendorType = operatingSystemVendorType;
    }

    public List<OperatingSystem> getOperatingSystems() {
        return operatingSystems;
    }

    public void setOperatingSystems(List<OperatingSystem> operatingSystems) {
        this.operatingSystems = operatingSystems;
    }
}
