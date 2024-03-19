package ee.fujitsu.delivery.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a vehicle in the system.
 * <p>
 * The VehicleEntity class is mapped to the "vehicle" table in the database.
 */
@Getter
@Setter
@Table(name = "vehicle")
@Entity
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
