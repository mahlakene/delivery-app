package ee.fujitsu.delivery.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity to map to "weather" table entries.
 */
@Getter
@Setter
@Entity
@Table(name = "weather")
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationName;
    private Long timeStamp;
    private Integer wmoCode;
    private Float airTemperature;
    private Float windSpeed;
    private String phenomenon;
}
