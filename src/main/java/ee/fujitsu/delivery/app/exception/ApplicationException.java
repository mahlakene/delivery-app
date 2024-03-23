package ee.fujitsu.delivery.app.exception;

/**
 * Custom exception class
 * <p>
 * The ApplicationException class extends RuntimeException and can be used to handle
 * exceptional situations specific to the application's runtime.
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }
}
