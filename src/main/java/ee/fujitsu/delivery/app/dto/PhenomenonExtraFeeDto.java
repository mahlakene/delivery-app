package ee.fujitsu.delivery.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PhenomenonExtraFeeDto {
    private String name;
    private Long vehicleId;
    private BigDecimal fee;
    private boolean forbidden = false;
}
