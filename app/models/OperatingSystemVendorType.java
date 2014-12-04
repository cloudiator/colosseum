package models;

/**
 * Created by daniel on 04.11.14.
 */
public enum OperatingSystemVendorType {

    NIX("*nix"),
    WINDOWS("windows");

    private final String text;

    private OperatingSystemVendorType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
