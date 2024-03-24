package ee.fujitsu.delivery.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BaseFeeDto {
    private Long cityId;
    private Long vehicleId;
    private BigDecimal fee;
}
