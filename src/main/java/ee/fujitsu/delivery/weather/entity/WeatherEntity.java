package ee.fujitsu.delivery.weather.entity;

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
    private Long timeStamp;
    private String stationName;
    private Integer wmo;
    private Float airTemperature;
    private Float windSpeed;
    private String weatherPhenomenon;
}
