package ee.fujitsu.delivery.app.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data class representing an error response.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
}
