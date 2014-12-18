package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by daniel on 31.10.14.
 */
@Entity
public class UserCredential extends Model {

    /**
     * Serial Version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The associated CloudApi
     */
    @ManyToOne(optional = false)
    private CloudApi cloudApi;

    /**
     * The associated Credentials
     */
    @ManyToOne(optional = false)
    private Credential credential;

    /**
     * The associated FrontendUser
     */
    @ManyToOne(optional = false)
    private FrontendUser frontendUser;

    public CloudApi getCloudApi() {
        return cloudApi;
    }

    public void setCloudApi(CloudApi cloudApi) {
        checkNotNull(cloudApi);
        this.cloudApi = cloudApi;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        checkNotNull(credential);
        this.credential = credential;
    }

    public FrontendUser getFrontendUser() {
        return frontendUser;
    }

    public void setFrontendUser(FrontendUser frontendUser) {
        checkNotNull(frontendUser);
        this.frontendUser = frontendUser;
    }
}
