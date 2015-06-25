package dtos.validation;

/**
 * Created by daniel on 02.06.15.
 */
public class ValidationErrorMessage {

    private final String message;

    private ValidationErrorMessage(String message) {
        this.message = message;
    }

    public static ValidationErrorMessage of(String message) {
        return new ValidationErrorMessage(message);
    }

    public String getMessage() {
        return message;
    }
}
