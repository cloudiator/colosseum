package models;

import models.generic.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Daniel Seybold on 23.11.2016.
 */
@Entity public class PlatformApi extends Model {

    @Column(nullable = false) private String internalProviderName;
    @Column(unique = true, nullable = false) private String name;
    @OneToMany(mappedBy = "platformApi") private List<Platform> platforms;



    /**
     * Empty constructor for hibernate.
     */
    protected PlatformApi() {
    }

    public PlatformApi(String name, String internalProviderName) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        checkNotNull(internalProviderName);
        checkArgument(!internalProviderName.isEmpty());
        this.name = name;
        this.internalProviderName = internalProviderName;
    }

    public String getInternalProviderName() {
        return internalProviderName;
    }

    public void setInternalProviderName(String internalProviderName) {
        this.internalProviderName = internalProviderName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }
}
