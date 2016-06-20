package components.model;

/**
 * Created by daniel on 19.06.16.
 */
public class ValidationMessage {

    private final String message;
    private final Type type;

    private ValidationMessage(String message, Type type) {
        this.message = message;
        this.type = type;
    }

    public static ValidationMessage of(String message, Type type) {
        return new ValidationMessage(message, type);
    }

    public String message() {
        return message;
    }

    public Type type() {
        return type;
    }

    public enum Type {
        WARNING,
        ERROR
    }
}
