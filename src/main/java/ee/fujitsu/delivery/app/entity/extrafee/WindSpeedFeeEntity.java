package ee.fujitsu.delivery.app.entity.extrafee;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entity class representing wind speed extra fee in the system.
 * <p>
 * The WindSpeedFeeEntity class is mapped to the "wind_speed_fee" table in the database.
 */
@Getter
@Setter
@Table(name = "wind_speed_fee")
@Entity
public class WindSpeedFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float lowerBound;
    private Float upperBound;
    private Long vehicleId;
    private BigDecimal fee;
    private boolean forbidden = false;
}
