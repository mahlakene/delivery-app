package ee.fujitsu.delivery.weather.controller;

import ee.fujitsu.delivery.weather.dto.WeatherDto;
import ee.fujitsu.delivery.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing weather data.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    /**
     * Retrieves weather data.
     */
    @PostMapping
    public void registerWeatherData() {
        weatherService.registerWeatherData();
    }
}
