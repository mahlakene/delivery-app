package ee.fujitsu.delivery.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entity class representing a base fee in the system.
 * <p>
 * The BaseFeeEntity class is mapped to the "base_fee" table in the database.
 */
@Getter
@Setter
@Table(name = "base_fee")
@Entity
public class BaseFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cityId;
    private Long vehicleId;
    private BigDecimal fee;
}
