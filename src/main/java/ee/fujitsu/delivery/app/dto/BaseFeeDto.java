package ee.fujitsu.delivery.app.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for base fee (city and vehicle combination).
 */
@Data
public class BaseFeeDto {
    private Long cityId;
    private Long vehicleId;
    private BigDecimal fee;
}
