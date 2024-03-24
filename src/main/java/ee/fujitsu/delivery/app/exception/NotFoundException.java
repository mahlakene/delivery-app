package ee.fujitsu.delivery.app.exception;

/**
 * Exception that is thrown when some resource can not be found from the server.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
