package models;

import models.generic.Model;

import javax.annotation.Nullable;
import javax.persistence.*;

/**
 * Created by daniel on 18.05.15.
 */


/**
 * @todo somehow validate this constraint, only have one credential per cloud and frontend group (or find a better relational schema)
 */
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cloud_id", "frontendGroup_id"}))
@Entity public class KeyPair extends Model {

    @ManyToOne(optional = false) private Cloud cloud;
    @ManyToOne(optional = false) private FrontendGroup frontendGroup;

    private String privateKey;
    @Nullable @Column(nullable = true) private String publicKey;
    @Nullable @Column(nullable = true) private String cloudUuid;

    /**
     * No-args constructor for hibernate
     */
    private KeyPair() {
    }

    public KeyPair(Cloud cloud, FrontendGroup frontendGroup, String privateKey,
        @Nullable String publicKey, @Nullable String cloudUuid) {
        this.cloud = cloud;
        this.frontendGroup = frontendGroup;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.cloudUuid = cloudUuid;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public FrontendGroup getFrontendGroup() {
        return frontendGroup;
    }

    public void setFrontendGroup(FrontendGroup frontendGroup) {
        this.frontendGroup = frontendGroup;
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

    @Nullable public String getCloudUuid() {
        return cloudUuid;
    }

    public void setCloudUuid(@Nullable String cloudUuid) {
        this.cloudUuid = cloudUuid;
    }
}
