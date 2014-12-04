package models;

import models.generic.Model;

import javax.persistence.Entity;

/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class CloudApi extends Model {

    /**
     * Empty constructor for hibernate.
     */
    public CloudApi() {
    }

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    private Api api;

    private String endpoint;

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
