package cloud;

import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 28.04.15.
 */
public class CloudCredentialLocationId {

    private static final String DELIMITER = "/";
    private final String baseId;
    private final String cloud;
    private final String credential;


    private CloudCredentialLocationId(String baseId, String cloud, String credential) {
        this.baseId = baseId;
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
        return baseId;
    }

    public String location() {
        return IdScopeByLocations.from(baseId).getLocationId();
    }

    public String baseId() {
        return IdScopeByLocations.from(baseId).getId();
    }

    public String id() {
        return cloud + DELIMITER + credential + DELIMITER + baseId;
    }

    public static CloudCredentialLocationId of(String swordId, String cloud, String credential) {
        return new CloudCredentialLocationId(swordId, cloud, credential);
    }

    public static CloudCredentialLocationId of(String id) {

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

        return new CloudCredentialLocationId(swordId, cloud, credential);

    }


}
