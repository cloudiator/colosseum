package models.service;

/**
 * Created by daniel on 17.11.15.
 */
public class IllegalColumnException extends Exception {

    public IllegalColumnException() {
        super();
    }

    public IllegalColumnException(String message) {
        super(message);
    }

    public IllegalColumnException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalColumnException(Throwable cause) {
        super(cause);
    }

    protected IllegalColumnException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
