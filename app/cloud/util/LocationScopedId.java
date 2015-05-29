package cloud.util;

/**
 * Created by daniel on 28.05.15.
 */
public class LocationScopedId extends CloudScopedId {

    private final String location;

    protected LocationScopedId(String baseId, String cloud, String credential, String location) {
        super(baseId, cloud, credential);
        this.location = location;
    }

    public String location() {
        return location;
    }

    @Override public String id() {
        return cloud() + ScopedIds.DELIMITER + credential() + ScopedIds.DELIMITER + location
            + ScopedIds.DELIMITER + baseId();
    }
}
