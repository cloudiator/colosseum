package models;

/**
 * Created by daniel on 04.11.14.
 */
public enum LocationScope {
    REGION("Region"),
    ZONE("Zone"),
    HOST("Host");

    private final String text;

    private LocationScope(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
