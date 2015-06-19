package cloud.util;

import de.uniulm.omi.cloudiator.sword.api.util.IdScopedByLocation;
import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

/**
 * Created by daniel on 28.05.15.
 */
public class ScopedIds {

    public static final String DELIMITER = "/";

    public static CloudScopedId of(String swordId, String cloud, String credential) {
        return new CloudScopedId(swordId, cloud, credential);
    }

    public static LocationScopedId of(String swordId, String cloud, String credential,
        String location) {
        return new LocationScopedId(swordId, cloud, credential, location);
    }

    public static IdScopedByLocation of(String swordId, String location) {
        return IdScopeByLocations.from(location, swordId);
    }

    //check if the sword id holds a location part
    public static CloudScopedId of(String id) {
        String[] parts = id.split(DELIMITER);

    }
}
