package ee.fujitsu.delivery.app.entity.extrafee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entity class representing air temperature extra fee in the system.
 * <p>
 * The AirTemperatureFeeEntity class is mapped to the "air_temperature_fee" table in the database.
 */
@Getter
@Setter
@Table(name = "air_temperature_fee")
@Entity
public class AirTemperatureFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float lowerBound;
    private Float upperBound;
    private Long vehicleId;
    private BigDecimal fee;
}
