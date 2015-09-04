package models.scalability;

/**
 * Created by Frank on 25.05.2015.
 */
public enum FlowOperator {

    MAP("MAP"),
    REDUCE("REDUCE");

    private final String text;

    private FlowOperator(final String text) {
        this.text = text;
    }

    @Override public String toString() {
        return text;
    }
}