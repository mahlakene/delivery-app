package ee.fujitsu.delivery.weather.service;

import ee.fujitsu.delivery.weather.dto.WeatherDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Service class for getting weather data.
 *
 * The data belong to and originate from the Estonian Environmental Agency ("ilmateenistus.ee").
 */
@Service
@RequiredArgsConstructor
@Component
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String requestUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    public WeatherDto getWeatherInfo() {
        //WeatherDto[] responseArray = restTemplate.getForObject(requestUrl, WeatherDto[].class);
        //System.out.println(responseArray[3]);
        //return null;
        try {
            URL url = new URL(requestUrl);
            InputStream inputStream = url.openStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            String title = document.getElementsByTagName("station").item(0).getTextContent();
            System.out.println("station: " + title);

            inputStream.close();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}
