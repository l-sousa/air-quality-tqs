package tqs.assignment.airquality.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tqs.assignment.airquality.entities.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AirPollutionReportServiceTest {

    @Mock
    private RestTemplate rest_template;

    @InjectMocks
    private AirPollutionReportService service;

    @Test
    void whenValidLocation_thenReturnAirQuality() throws MalformedURLException, JsonProcessingException {

        String foz_coa = "Rua da Lameira, Vila Nova de Foz Côa, Guarda, Portugal";
        Location location = new Location(foz_coa);

        Map<String, Pollutant> pollutants = new TreeMap<>();

        Pollutant co = new Pollutant("CO", "Carbon monoxide", 106.02, "ppb");
        Pollutant no2 = new Pollutant("NO2", "Nitrogen dioxide", 0.48, "ppb");
        Pollutant o3 = new Pollutant("O3", "Ozone", 37.32, "ppb");
        Pollutant pm10 = new Pollutant("PM10", "Inhalable particulate matter (<10µm)", 5.58, "ug/m3");
        Pollutant pm2_5 = new Pollutant("PM2.5", "Fine particulate matter (<2.5µm)", 1.73, "ug/m3");
        Pollutant so2 = new Pollutant("SO2", "Sulfur dioxide", 0.06, "ppb");

        AirQuality hard_coded_aq = new AirQuality(location);

        hard_coded_aq.addPollutant(co);
        hard_coded_aq.addPollutant(no2);
        hard_coded_aq.addPollutant(o3);
        hard_coded_aq.addPollutant(pm10);
        hard_coded_aq.addPollutant(pm2_5);
        hard_coded_aq.addPollutant(so2);

        ResponseEntity<String> response = ResponseEntity.accepted().body("<200,{\"metadata\": null, \"data\": {\"datetime\": \"2021-05-10T16:00:00Z\", \"data_available\": true, \"pollutants\": {\"co\": {\"display_name\": \"CO\", \"full_name\": \"Carbon monoxide\", \"concentration\": {\"value\": 106.02, \"units\": \"ppb\"}}, \"no2\": {\"display_name\": \"NO2\", \"full_name\": \"Nitrogen dioxide\", \"concentration\": {\"value\": 0.48, \"units\": \"ppb\"}}, \"o3\": {\"display_name\": \"O3\", \"full_name\": \"Ozone\", \"concentration\": {\"value\": 37.32, \"units\": \"ppb\"}}, \"pm10\": {\"display_name\": \"PM10\", \"full_name\": \"Inhalable particulate matter (<10\\u00b5m)\", \"concentration\": {\"value\": 5.58, \"units\": \"ug/m3\"}}, \"pm25\": {\"display_name\": \"PM2.5\", \"full_name\": \"Fine particulate matter (<2.5\\u00b5m)\", \"concentration\": {\"value\": 1.73, \"units\": \"ug/m3\"}}, \"so2\": {\"display_name\": \"SO2\", \"full_name\": \"Sulfur dioxide\", \"concentration\": {\"value\": 0.06, \"units\": \"ppb\"}}}}, \"error\": null},[Content-Type:\"application/json\", Vary:\"Accept-Encoding\", Access-Control-Allow-Headers:\"Content-Type,Authorization\", Access-Control-Allow-Methods:\"GET,PUT,POST,DELETE\", Access-Control-Allow-Origin:\"*\", X-Appengine-Log-Flush-Count:\"1\", X-Cloud-Trace-Context:\"1554d21c333e36a29b66c44f364e7246\", Date:\"Mon, 10 May 2021 16:10:39 GMT\", Server:\"Google Frontend\", Content-Length:\"849\"]>");

        URL url = new URL(AddressToCoordinatesService.PS_API_URL + String.format("&query=%s", foz_coa));

        when(rest_template.getForEntity(url.toString(), String.class)).thenReturn(response);

        AirQuality mocked_aq = service.getDataByLocation(foz_coa);

        assertEquals(mocked_aq, hard_coded_aq);
    }
}