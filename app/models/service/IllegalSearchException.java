package models.service;

/**
 * Created by daniel on 17.11.15.
 */
public class IllegalSearchException extends Exception {

    public IllegalSearchException() {
    }

    public IllegalSearchException(String message) {
        super(message);
    }

    public IllegalSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalSearchException(Throwable cause) {
        super(cause);
    }

    public IllegalSearchException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
