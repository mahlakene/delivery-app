package ee.fujitsu.delivery.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for defining beans and application-wide settings.
 */
@Configuration
public class WeatherConfig {

    /**
     * Configures and provides a RestTemplate bean for making HTTP requests.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
