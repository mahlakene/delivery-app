package ee.fujitsu.delivery.app.dto;

import lombok.Data;

/**
 * Dto representing weather info.
 */
@Data
public class WeatherDto {
    private Long id;
    private Long timeStamp;
    private String stationName;
    private Integer wmo;
    private Float airTemperature;
    private Float windSpeed;
    private String weatherPhenomenon;
}
