package ee.fujitsu.delivery.weather.controller;

import ee.fujitsu.delivery.weather.dto.WeatherDto;
import ee.fujitsu.delivery.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping
    public WeatherDto getWeatherData() {
        return weatherService.getWeatherInfo();
    }
}
