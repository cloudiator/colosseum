package models;

import models.generic.NamedModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class VirtualMachine extends NamedModel {

    public VirtualMachine() {
    }

    @ManyToOne(optional = false)
    private Cloud cloud;

    @ManyToOne(optional = false)
    private CloudImage cloudImage;

    @ManyToOne(optional = false)
    private CloudHardware cloudHardware;

    @ManyToOne(optional = false)
    private CloudLocation cloudLocation;

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    public CloudImage getCloudImage() {
        return cloudImage;
    }

    public void setCloudImage(CloudImage cloudImage) {
        checkNotNull(cloudImage);
        this.cloudImage = cloudImage;
    }

    public CloudHardware getCloudHardware() {
        return cloudHardware;
    }

    public void setCloudHardware(CloudHardware cloudHardware) {
        checkNotNull(cloudHardware);
        this.cloudHardware = cloudHardware;
    }

    public CloudLocation getCloudLocation() {
        return cloudLocation;
    }

    public void setCloudLocation(CloudLocation cloudLocation) {
        checkNotNull(cloudLocation);
        this.cloudLocation = cloudLocation;
    }
}
