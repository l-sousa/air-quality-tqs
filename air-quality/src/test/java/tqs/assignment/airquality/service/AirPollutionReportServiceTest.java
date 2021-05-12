package tqs.assignment.airquality.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tqs.assignment.airquality.entities.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AirPollutionReportServiceTest {

    @InjectMocks
    private AirPollutionReportService service;

    @Mock
    private RestTemplate restTemplate;


    @Test
    void whenValidLocation_thenReturnAirQuality() throws JsonProcessingException, URISyntaxException, MalformedURLException {

        String foz_coa = "Rua da Lameira, Vila Nova de Foz Coa, Guarda, Portugal".replace(" ", "+");
        Location location = new Location(foz_coa);
        location.setCoordinates(new Coordinates(41.083118, -7.140839));

        Map<String, Pollutant> pollutants = new TreeMap<>();
        Pollutant co = new Pollutant("CO", "Carbon monoxide", 118.81, "ppb");
        Pollutant no2 = new Pollutant("NO2", "Nitrogen dioxide", 0.76, "ppb");
        Pollutant o3 = new Pollutant("O3", "Ozone", 30.42, "ppb");
        Pollutant pm10 = new Pollutant("PM10", "Inhalable particulate matter (<10µm)", 10.07, "ug/m3");
        Pollutant pm2_5 = new Pollutant("PM2.5", "Fine particulate matter (<2.5µm)", 4.26, "ug/m3");
        Pollutant so2 = new Pollutant("SO2", "Sulfur dioxide", 0.13, "ppb");

        AirQuality hard_coded_aq = new AirQuality(location);

        hard_coded_aq.addPollutant(co);
        hard_coded_aq.addPollutant(no2);
        hard_coded_aq.addPollutant(o3);
        hard_coded_aq.addPollutant(pm10);
        hard_coded_aq.addPollutant(pm2_5);
        hard_coded_aq.addPollutant(so2);

        ResponseEntity<String> response = new ResponseEntity<>("{\"metadata\": null, \"data\": {\"datetime\": \"2021-05-11T01:00:00Z\", \"data_available\": true, \"pollutants\": {\"co\": {\"display_name\": \"CO\", \"full_name\": \"Carbon monoxide\", \"concentration\": {\"value\": 118.81, \"units\": \"ppb\"}}, \"no2\": {\"display_name\": \"NO2\", \"full_name\": \"Nitrogen dioxide\", \"concentration\": {\"value\": 0.76, \"units\": \"ppb\"}}, \"o3\": {\"display_name\": \"O3\", \"full_name\": \"Ozone\", \"concentration\": {\"value\": 30.42, \"units\": \"ppb\"}}, \"pm10\": {\"display_name\": \"PM10\", \"full_name\": \"Inhalable particulate matter (<10\\u00b5m)\", \"concentration\": {\"value\": 10.07, \"units\": \"ug/m3\"}}, \"pm25\": {\"display_name\": \"PM2.5\", \"full_name\": \"Fine particulate matter (<2.5\\u00b5m)\", \"concentration\": {\"value\": 4.26, \"units\": \"ug/m3\"}}, \"so2\": {\"display_name\": \"SO2\", \"full_name\": \"Sulfur dioxide\", \"concentration\": {\"value\": 0.13, \"units\": \"ppb\"}}}}, \"error\": null},[Content-Type:\"application/json\", Vary:\"Accept-Encoding\", Access-Control-Allow-Headers:\"Content-Type,Authorization\", Access-Control-Allow-Methods:\"GET,PUT,POST,DELETE\", Access-Control-Allow-Origin:\"*\", X-Appengine-Log-Flush-Count:\"1\", X-Cloud-Trace-Context:\"4a508d57376ce5897309bae7710dbd28\", Date:\"Tue, 11 May 2021 01:35:46 GMT\", Server:\"Google Frontend\", Content-Length:\"850\"]", HttpStatus.OK);
        URI url = new URI(AirPollutionReportService.BM_API_URL_CURRENT + String.format("&lat=%f&lon=%f&features=pollutants_concentrations", location.getCoordinates().getLatitude(), location.getCoordinates().getLongitude()));
        Mockito.when(restTemplate.getForEntity(url, String.class)).thenReturn(response);
        AirQuality mocked_aq = service.getDataByLocation(location);
        Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForEntity(url, String.class);
        assertEquals(mocked_aq, hard_coded_aq);

    }

    @Test
    void run() throws JsonProcessingException, URISyntaxException, MalformedURLException {



    }

}