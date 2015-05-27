package cloud;

import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 28.04.15.
 */
public class ScopedId {

    private static final String DELIMITER = "/";

    private final String baseId;
    private final String location;
    private final String cloud;
    private final String credential;

    private ScopedId(String swordId, String cloud, String credential) {
        this.swordId = swordId;
        this.cloud = cloud;
        this.credential = credential;
    }

    public String cloud() {
        return cloud;
    }

    public String credential() {
        return credential;
    }

    public String swordId() {
        return swordId;
    }

    public String location() {
        return IdScopeByLocations.from(swordId).getLocationId();
    }

    public String baseId() {
        return IdScopeByLocations.from(swordId).getId();
    }

    public String id() {
        return cloud + DELIMITER + credential + DELIMITER + swordId;
    }

    public static ScopedId of(String swordId, String cloud, String credential) {
        return new ScopedId(swordId, cloud, credential);
    }

    public static ScopedId of(String id) {

        String[] parts = id.split(DELIMITER);
        checkArgument(parts.length >= 3,
            "Expected at least 2 occurrences of delimiter (" + DELIMITER + ") in given id.");
        final String cloud = parts[0];
        final String credential = parts[1];

        String swordId = "";
        for (int i = 2; i < parts.length; i++) {
            if (i != 2) {
                swordId += DELIMITER;
            }
            swordId += parts[i];
        }

        return new ScopedId(swordId, cloud, credential);

    }


}
