package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkNotNull;

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

    /**
     * The Api.
     */
    @ManyToOne(optional = false)
    private Api api;

    /**
     * The Cloud.
     */
    @ManyToOne(optional = false)
    private Cloud cloud;

    /**
     * The endpoint of the CloudApi
     */
    private String endpoint;

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        checkNotNull(api);
        this.api = api;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        checkNotNull(cloud);
        this.cloud = cloud;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        checkNotNull(endpoint);
        this.endpoint = endpoint;
    }


}
