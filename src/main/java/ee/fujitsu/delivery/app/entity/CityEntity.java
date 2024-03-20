package ee.fujitsu.delivery.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a city in the system.
 * <p>
 * The CityEntity class is mapped to the "city" table in the database.
 */
@Getter
@Setter
@Table(name = "city")
@Entity
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer weatherStationWmo;
}
