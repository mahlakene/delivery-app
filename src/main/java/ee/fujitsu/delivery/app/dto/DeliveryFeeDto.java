package ee.fujitsu.delivery.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeliveryFeeDto {
    private Long cityId;
    private Long vehicleId;
    private BigDecimal baseFee;
    private BigDecimal extraFee;
    private BigDecimal totalFee;
}
