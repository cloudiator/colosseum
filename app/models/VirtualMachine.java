package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class VirtualMachine {

    @ManyToOne
    private Cloud cloud;

    @ManyToOne
    private CloudImage cloudImage;

    @ManyToOne
    private CloudHardware cloudHardware;

    @ManyToOne
    private CloudLocation cloudLocation;
}
