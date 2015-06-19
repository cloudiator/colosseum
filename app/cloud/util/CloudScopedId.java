package cloud.util;

/**
 * Created by daniel on 28.04.15.
 */
public class CloudScopedId {

    private final String baseId;
    private final String cloud;
    private final String credential;

    protected CloudScopedId(String baseId, String cloud, String credential) {
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

    public String baseId() {
        return baseId;
    }

    public String swordId() {
        return baseId;
    }

    public String id() {
        return credential + ScopedIds.DELIMITER + cloud + ScopedIds.DELIMITER + baseId;
    }



}
