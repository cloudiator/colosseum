package dtos.generic.impl;

/**
 * Created by daniel on 18.12.14.
 */
public enum Rel {

    SELF("self");

    private final String text;

    private Rel(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
