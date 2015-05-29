package cloud.util;

/**
 * Created by daniel on 28.05.15.
 */
public class ScopedIds {

    public static final String DELIMITER = "/";

    public static CloudScopedId of(String swordId, String cloud, String credential) {

    }

    //check if the sword id holds a location part
    public static CloudScopedId of(String id) {
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
        return new CloudScopedId(swordId, cloud, credential);
    }
}
