package models;

import models.generic.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by daniel on 04.11.14.
 */
@Entity
public class OperatingSystem extends Model {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    private OperatingSystemArchitecture operatingSystemArchitecture;

    @OneToMany(mappedBy = "operatingSystem")
    private List<Image> images;

    @ManyToOne
    private OperatingSystemVendor operatingSystemVendor;


    private String version;

    public OperatingSystemArchitecture getOperatingSystemArchitecture() {
        return operatingSystemArchitecture;
    }

    public void setOperatingSystemArchitecture(OperatingSystemArchitecture operatingSystemArchitecture) {
        this.operatingSystemArchitecture = operatingSystemArchitecture;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public OperatingSystemVendor getOperatingSystemVendor() {
        return operatingSystemVendor;
    }

    public void setOperatingSystemVendor(OperatingSystemVendor operatingSystemVendor) {
        this.operatingSystemVendor = operatingSystemVendor;
    }
}
