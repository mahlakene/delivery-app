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
 * Entity class representing weather phenomenon extra fee in the system.
 * <p>
 * The PhenomenonFeeEntity class is mapped to the "phenomenon_fee" table in the database.
 */
@Getter
@Setter
@Table(name = "phenomenon_fee")
@Entity
public class PhenomenonFeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long vehicleId;
    private BigDecimal fee;
    private boolean forbidden = false;
}
