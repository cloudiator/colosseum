package models;

import models.generic.NamedModel;

import javax.persistence.Entity;

/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class Api extends NamedModel {

    /**
     * Empty constructor. Needed by hibernate.
     */
    public Api(){

    }

    public Api(String name){
        super(name);
    }

    /**
     * Serial Version.
     */
    private static final long serialVersionUID = 1L;

}
