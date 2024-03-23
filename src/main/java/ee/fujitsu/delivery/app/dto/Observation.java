package ee.fujitsu.delivery.app.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * DTO object for reading weather info.
 */
@Data
@JacksonXmlRootElement(localName = "observations")
public class Observation {
    @JacksonXmlProperty(localName = "timestamp")
    private long timestamp;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "station")
    private Station[] stations;
}
