package ee.fujitsu.delivery.app.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ee.fujitsu.delivery.app.dto.Observation;
import ee.fujitsu.delivery.app.dto.Station;
import ee.fujitsu.delivery.app.dto.WeatherDto;
import ee.fujitsu.delivery.app.entity.WeatherEntity;
import ee.fujitsu.delivery.app.exception.NotFoundException;
import ee.fujitsu.delivery.app.mapper.WeatherMapper;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for getting weather data.
 *
 * The data belong to and originate from the Estonian Environmental Agency ("ilmateenistus.ee").
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class WeatherService {
    private final String requestUrl = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;
    private final WeatherMapper weatherMapper = Mappers.getMapper(WeatherMapper.class);

    /**
     * Read (latest) weather data from xml file to Observation and Station DTO objects.
     */
    public void registerWeatherData() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            URL url = new URL(requestUrl);
            Observation observations = xmlMapper.readValue(url, Observation.class);
            saveWeatherToDatabase(observations.getStations(), observations.getTimestamp());
        } catch (Exception e) {
            log.error("Error requesting weather data", e);
        }
    }

    /**
     * Save only those cities weather info into weather database that exist in City's database.
     * @param stations All stations weather ifo
     * @param timeStamp timestamp
     */
    private void saveWeatherToDatabase(Station[] stations, long timeStamp) {
        List<Integer> allWmos = cityRepository.findAllWeatherStationWmos();
        List<WeatherEntity> entities = new ArrayList<>();
        for (Station station : stations) {
            Integer wmo = station.getWmoCode();
            if (allWmos.contains(wmo)) {
                WeatherEntity entity = weatherMapper.toEntity(station);
                entity.setTimeStamp(timeStamp);
                entities.add(entity);
            }
        }
        weatherRepository.saveAll(entities);
    }

    public WeatherDto getWeatherInfo(Integer wmoCode, LocalDateTime dateTime) {
        List<WeatherEntity> allDataOfCity = weatherRepository.findAllByWmoCodeOrderByTimeStampDesc(wmoCode);
        if (allDataOfCity.isEmpty()) {
            throw new NotFoundException("No weather data about the given city.");
        }
        if (dateTime == null) {
            return weatherMapper.toDto(allDataOfCity.get(0));
        }
        ZoneId zoneId = ZoneId.of("Europe/Tallinn");  // Estonia's time zone
        long targetUnixTimestamp = dateTime.atZone(zoneId).toEpochSecond();
        WeatherEntity closestWeather = null;
        long minDifference = Long.MAX_VALUE;
        for (WeatherEntity weather : allDataOfCity) {
            long currentUnixTimestamp = weather.getTimeStamp();
            long difference = Math.abs(targetUnixTimestamp - currentUnixTimestamp);
            if (difference < minDifference) {
                minDifference = difference;
                closestWeather = weather;
            }
        }
        return weatherMapper.toDto(closestWeather);
    }
}
