package ee.fujitsu.delivery.weather.service;

import ee.fujitsu.delivery.app.entity.CityEntity;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.weather.dto.WeatherDto;
import ee.fujitsu.delivery.weather.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for getting weather data.
 *
 * The data belong to and originate from the Estonian Environmental Agency ("ilmateenistus.ee").
 */
@Service
@RequiredArgsConstructor
@Component
public class WeatherService {
    private final String requestUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;

    public void registerWeatherData() {
        List<Node> cities = getCitiesData();
        saveWeatherInfo(cities);
    }

    public void saveWeatherInfo(List<Node> cities) {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        for (Node city : cities) {
            //String wmo = (String) xpath.evaluate("wmocode/text()", city, XPathConstants.STRING);
        }
    }

    private void parseData(XPath xpath, Node city, Integer wmo) throws XPathExpressionException {
        String stationName = (String) xpath.evaluate("name/text()", city, XPathConstants.STRING);
        String airTemperature = (String) xpath.evaluate("name/text()", city, XPathConstants.STRING);

    }

    public List<Node> getCitiesData() {
        List<Node> expectedCities = new ArrayList<>();
        try {
            URL url = new URL(requestUrl);
            InputStream inputStream = url.openStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            NodeList nodeList = (NodeList) xpath.evaluate("//station", document, XPathConstants.NODESET);

            List<Integer> allWmos = cityRepository.findAllWeatherStationWmos();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String wmo = (String) xpath.evaluate("wmocode/text()", node, XPathConstants.STRING);
                if (!wmo.isEmpty()) {
                    int wmoInt = Integer.parseInt(wmo);
                    if (allWmos.contains(wmoInt)) {
                        parseData(xpath, node, wmoInt);
                        //expectedCities.add(node);
                    }
                }
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expectedCities;
    }
}
