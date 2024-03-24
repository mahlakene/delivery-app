package ee.fujitsu.delivery.app.exception;

/**
 * Exception to be thrown when requesting weather fails.
 */
public class WeatherRequestException extends RuntimeException {

    public WeatherRequestException(String message) {
        super(message);
    }
}
