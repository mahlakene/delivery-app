package ee.fujitsu.delivery.app.exception;

/**
 * Exception for the situation where new fee is being registered but fee already exist.
 */
public class FeeAlreadyExists extends ApplicationException {
    public FeeAlreadyExists(String message) {
        super(message);
    }
}
