package models;

/**
 * Created by daniel on 04.11.14.
 */
public enum OperatingSystemArchitecture {

    AMD64("amd64"),
    I386("i386");

    private final String text;

    private OperatingSystemArchitecture(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
