package models;

import models.generic.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by daniel on 10.11.14.
 */
@Entity
public class LocationCode extends Model {

    @ManyToMany(mappedBy = "locationCodes")
    private List<Location> locations;

    private String iso3166Alpha2;

    public String getIso3166Alpha2() {
        return iso3166Alpha2;
    }

    public void setIso3166Alpha2(String iso3166Alpha2) {
        this.iso3166Alpha2 = iso3166Alpha2;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
