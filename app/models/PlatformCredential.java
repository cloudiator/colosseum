package models;

import models.generic.Model;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Daniel Seybold on 23.11.2016.
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"platform_id", "tenant_id"})) @Entity
public class PlatformCredential extends Model {

    @Column(nullable = false) private String user;

    @Lob
    @Column(nullable = false) private String secret;

    @ManyToOne(optional = false) private Platform platform;

    @ManyToOne(optional = false) private Tenant tenant;

    /**
     * Empty constructor for hibernate.
     */
    public PlatformCredential(){

    }

    public PlatformCredential(Platform platform, Tenant tenant, String user, String secret) {

        checkNotNull(platform);
        checkNotNull(tenant);
        checkNotNull(user);
        checkArgument(!user.isEmpty());
        checkNotNull(secret);
        checkArgument(!secret.isEmpty());

        this.platform = platform;
        this.tenant = tenant;
        this.user = user;
        this.secret = secret;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
