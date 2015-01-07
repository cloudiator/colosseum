package models;

import models.generic.Model;

import javax.persistence.*;

/**
 * Created by daniel on 19.12.14.
 */
@Entity
public class ApiAccessToken extends Model {

    private static final long VALIDITY = 5 * 60 * 1000;

    @Column(nullable = false)
    private long createdOn;

    @Column(nullable = false)
    private long expiresAt;

    @Lob
    @Column(nullable = false)
    private String token;

    @ManyToOne(optional = false)
    private FrontendUser frontendUser;

    /**
     * No-arg constructor for hibernate.
     */
    protected ApiAccessToken() {
    }

    public ApiAccessToken(final FrontendUser frontendUser, final String token) {
        this.frontendUser = frontendUser;
        this.token = token;
    }

    @PrePersist
    protected void onCreate() {
        long currentTime = System.currentTimeMillis();
        this.createdOn = currentTime;
        this.expiresAt = currentTime + VALIDITY;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public FrontendUser getFrontendUser() {
        return frontendUser;
    }

    public void setFrontendUser(FrontendUser frontendUser) {
        this.frontendUser = frontendUser;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    protected void setExpiresAt(final long expiresAt) {
        this.expiresAt = expiresAt;
    }
}
