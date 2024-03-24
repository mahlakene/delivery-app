package ee.fujitsu.delivery.app.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO object for extra fees with numerical ranges (wind speed, air temperature).
 */
@Data
public class NumericalExtraFeeDto {

    private Float lowerBound;
    private Float upperBound;
    private Long vehicleId;
    private BigDecimal fee;
    private boolean forbidden = false;
}
