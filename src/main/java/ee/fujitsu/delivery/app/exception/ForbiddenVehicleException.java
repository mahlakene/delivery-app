package ee.fujitsu.delivery.app.exception;

/**
 * Exception for the situation where vehicle is forbidden because of the weather conditions.
 */
public class ForbiddenVehicleException extends ApplicationException {
    public ForbiddenVehicleException(String message) {
        super(message);
    }
}
