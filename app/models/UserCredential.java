package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class UserCredential extends Model {

    /**
     * Serial Version.
     */
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private CloudApi cloudApi;

    @ManyToOne
    private Credential credential;

    @ManyToOne
    private FrontendUser frontendUser;

    public CloudApi getCloudApi() {
        return cloudApi;
    }

    public void setCloudApi(CloudApi cloudApi) {
        this.cloudApi = cloudApi;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public FrontendUser getFrontendUser() {
        return frontendUser;
    }

    public void setFrontendUser(FrontendUser frontendUser) {
        this.frontendUser = frontendUser;
    }
}
