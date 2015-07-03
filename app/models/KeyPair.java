package models;

import models.generic.RemoteModel;

import javax.annotation.Nullable;
import javax.persistence.*;

/**
 * Created by daniel on 18.05.15.
 */


/**
 * @todo somehow validate this constraint, only have one credential per cloud and frontend group (or find a better relational schema)
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cloud_id", "tenant_id"}))
@Entity public class KeyPair extends RemoteModel {

    @ManyToOne(optional = false) private Cloud cloud;
    @ManyToOne(optional = false) private Tenant tenant;

    private String privateKey;
    @Nullable @Column(nullable = true) private String publicKey;

    /**
     * No-args constructor for hibernate
     */
    protected KeyPair() {
    }

    public KeyPair(Cloud cloud, Tenant tenant, String privateKey,
        @Nullable String publicKey, @Nullable String remoteId) {
        super(remoteId);
        this.cloud = cloud;
        this.tenant = tenant;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Nullable public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(@Nullable String publicKey) {
        this.publicKey = publicKey;
    }
}
