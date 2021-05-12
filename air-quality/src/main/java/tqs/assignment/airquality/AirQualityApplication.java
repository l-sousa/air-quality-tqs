package tqs.assignment.airquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tqs.assignment.airquality.entities.AirQuality;
import tqs.assignment.airquality.entities.Cache;
import tqs.assignment.airquality.service.AddressToCoordinatesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AirQualityApplication {

    private Cache<ArrayList<AirQuality>> currentCache = new Cache();
    private Cache<AirQuality> forecastCache = new Cache();

    @Bean
    public Cache currentCache() {
        return currentCache;
    }

    @Bean
    public Cache forecastCache() {
        return forecastCache;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(AirQualityApplication.class, args);
    }


}
