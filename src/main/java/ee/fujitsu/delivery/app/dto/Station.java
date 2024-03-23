package ee.fujitsu.delivery.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * DTO object for reading weather info.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "station")
public class Station {
    @JacksonXmlProperty(localName = "name")
    private String stationName;
    @JacksonXmlProperty(localName = "wmocode")
    private int wmoCode;
    @JacksonXmlProperty(localName = "phenomenon")
    private String phenomenon;
    @JacksonXmlProperty(localName = "airtemperature")
    private Float airTemperature;
    @JacksonXmlProperty(localName = "windspeed")
    private Float windSpeed;
}
