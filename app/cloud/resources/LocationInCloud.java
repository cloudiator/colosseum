package cloud.resources;

import de.uniulm.omi.cloudiator.sword.api.domain.Location;

/**
 * Created by daniel on 28.05.15.
 */
public class LocationInCloud extends AbstractCredentialScopedResource<Location>
    implements Location, CloudScoped {

    private final Location location;

    public LocationInCloud(Location resource, String cloud, String credential) {
        super(resource, cloud, credential);
        this.location = resource;
    }

    @Override public boolean isAssignable() {
        return location.isAssignable();
    }
}
