package tqs.assignment.airquality.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AirPollutionReportServiceTest {


    @Test
    public void whenGivenLocation_thenReturnAccurateDataOfLocation() throws MalformedURLException, JsonProcessingException {
        AirPollutionReportService ap_service = new AirPollutionReportService();
        String foz_coa = "Rua da Lameira, Vila Nova de Foz Côa, Guarda, Portugal";
        String aveiro = "Universidade de Aveiro, Aveiro, Portugal";
        String new_york = "226 St. James Place, New York City, United States";
        String los_angeles = "4730 Azucena Road, Los Angeles, California, United States";

        List<String> allLocations = Arrays.asList(foz_coa, aveiro, new_york, los_angeles);
        ap_service.getDataByLocation(foz_coa);
    }
}