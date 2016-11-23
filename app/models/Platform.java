package models;

import com.google.common.collect.ImmutableList;
import models.generic.Model;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Daniel Seybold on 23.11.2016.
 */
@Entity
public class Platform extends Model {

    @Column(unique = true, nullable = false, updatable = false) private String name;
    @Nullable @Column(nullable = true) private String endpoint;
    @ManyToOne(optional = false) private PlatformApi platformApi;
    @OneToMany(mappedBy = "platform", cascade = CascadeType.REMOVE) private List<PlatformCredential>
            platformCredentials;

    /**
     * Empty constructor. Needed by hibernate.
     */
    protected Platform(){

    }

    public Platform(String name,@Nullable String endpoint, PlatformApi platformApi){

        checkNotNull(name);
        checkArgument(!name.isEmpty());
        if (endpoint != null) {
            checkArgument(!endpoint.isEmpty());
        }
        checkNotNull(platformApi);
        this.name = name;
        this.endpoint = endpoint;
        this.platformApi = platformApi;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Optional<String> getEndpoint() {
        return Optional.ofNullable(endpoint);
    }

    public PlatformApi api() {
        return platformApi;
    }

    public List<PlatformCredential> getPlatformCredentials() {

        return ImmutableList.copyOf(platformCredentials);
    }

    public void setPlatformCredentials(List<PlatformCredential> platformCredentials) {
        this.platformCredentials = platformCredentials;
    }
}
