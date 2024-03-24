package ee.fujitsu.delivery.app.service;

import ee.fujitsu.delivery.app.Utils;
import ee.fujitsu.delivery.app.dto.Station;
import ee.fujitsu.delivery.app.dto.WeatherDto;
import ee.fujitsu.delivery.app.entity.WeatherEntity;
import ee.fujitsu.delivery.app.repository.CityRepository;
import ee.fujitsu.delivery.app.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private CityRepository cityRepository;
    @InjectMocks
    private WeatherService weatherService;

    @Test
    void testSaveWeatherToDatabase() {
        Station[] stations = new Station[]{new Station(), new Station(), new Station()};
        when(cityRepository.findAllWeatherStationWmos()).thenReturn(List.of(26038, 26242, 41803));
        when(weatherRepository.saveAll(any())).thenReturn(List.of());

        weatherService.saveWeatherToDatabase(stations, 12345);

        verify(weatherRepository, times(1)).saveAll(any());
    }

    @Test
    void testGetWeatherInfo_Simple() {
        // Given
        List<WeatherEntity> allDataOfCity = Utils.getMockWeatherEntities();
        when(weatherRepository.findAllByWmoCodeOrderByTimeStampDesc(Mockito.anyInt())).thenReturn(allDataOfCity);

        // When
        WeatherDto result = weatherService.getWeatherInfo(26038, null);

        // Then
        assertEquals(8F, result.getWindSpeed());
        assertEquals("Rain", result.getPhenomenon());
    }

    @Test
    void testGetWeatherInfo_DateTimeGiven() {
        // Given
        List<WeatherEntity> allDataOfCity = Utils.getMockWeatherEntities();
        when(weatherRepository.findAllByWmoCodeOrderByTimeStampDesc(Mockito.anyInt())).thenReturn(allDataOfCity);

        // When
        WeatherDto result = weatherService.getWeatherInfo(26038,
                LocalDateTime.of(1970, 1, 1, 8, 10, 22));

        // Then
        assertEquals(22F, result.getWindSpeed());
        assertEquals("Light Snow", result.getPhenomenon());

        // When
        WeatherDto result2 = weatherService.getWeatherInfo(26038,
                LocalDateTime.of(1970, 1, 1, 6, 10, 22));

        // Then
        assertEquals(8, result2.getWindSpeed());
        assertEquals("Rain", result2.getPhenomenon());
    }

}
