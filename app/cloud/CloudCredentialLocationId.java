package cloud;

import de.uniulm.omi.cloudiator.sword.core.util.IdScopeByLocations;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by daniel on 28.04.15.
 */
public class CloudCredentialLocationId {

    private static final String DELIMITER = "/";
    private final String swordId;
    private final String cloud;
    private final String credential;


    private CloudCredentialLocationId(String swordId, String cloud, String credential) {
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

    public String rawId() {
        return IdScopeByLocations.from(swordId).getId();
    }

    public String id() {
        return cloud + DELIMITER + credential + DELIMITER + swordId;
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
