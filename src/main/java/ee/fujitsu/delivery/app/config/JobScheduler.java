package ee.fujitsu.delivery.app.config;

import ee.fujitsu.delivery.app.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JobScheduler {

    private final WeatherService weatherService;

    /**
     * Update weather info once every hour, 15 minutes after a full hour.
     */
    @Scheduled(cron = "0 15 * * * *")
    public void updateWeatherInfo() {
        System.out.println("YAY");
        weatherService.registerWeatherData();
    }
}
