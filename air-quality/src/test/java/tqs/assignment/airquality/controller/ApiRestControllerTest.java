package tqs.assignment.airquality.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class ApiRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestTemplate restTemplate;

    private String location = "Aveiro";

    @Test
    public void whenGetCurrentDateOfLocation_thenReturnAirQaulity() throws Exception {
        String mock_response = "{ \"location\": { \"address\": \"Aveiro\", \"coordinates\": { \"latitude\": 40.635013, \"longitude\": -8.651136 } }, \"pollutants\": { \"Carbon monoxide\": { \"display_name\": \"CO\", \"full_name\": \"Carbon monoxide\", \"value\": 182.58, \"units\": \"ppb\" }, \"Fine particulate matter (<2.5µm)\": { \"display_name\": \"PM2.5\", \"full_name\": \"Fine particulate matter (<2.5µm)\", \"value\": 6.3, \"units\": \"ug/m3\" }, \"Inhalable particulate matter (<10µm)\": { \"display_name\": \"PM10\", \"full_name\": \"Inhalable particulate matter (<10µm)\", \"value\": 6.93, \"units\": \"ug/m3\" }, \"Nitrogen dioxide\": { \"display_name\": \"NO2\", \"full_name\": \"Nitrogen dioxide\", \"value\": 6.61, \"units\": \"ppb\" }, \"Ozone\": { \"display_name\": \"O3\", \"full_name\": \"Ozone\", \"value\": 25.48, \"units\": \"ppb\" }, \"Sulfur dioxide\": { \"display_name\": \"SO2\", \"full_name\": \"Sulfur dioxide\", \"value\": 4.12, \"units\": \"ppb\" } }, \"date\": null }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(mock_response);

        System.out.println(root);

        mvc.perform(get("/api/current/" + location)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("location.coordinates.latitude", is(root.path("location").path("coordinates").get("latitude"))));

    }

    @Test
    public void whenGetForecasteOfLocation_thenReturnAirQaulity() throws Exception {
        String mock_response = "{ \"location\": { \"address\": \"Aveiro\", \"coordinates\": { \"latitude\": 40.635013, \"longitude\": -8.651136 } }, \"pollutants\": { \"Carbon monoxide\": { \"display_name\": \"CO\", \"full_name\": \"Carbon monoxide\", \"value\": 182.58, \"units\": \"ppb\" }, \"Fine particulate matter (<2.5µm)\": { \"display_name\": \"PM2.5\", \"full_name\": \"Fine particulate matter (<2.5µm)\", \"value\": 6.3, \"units\": \"ug/m3\" }, \"Inhalable particulate matter (<10µm)\": { \"display_name\": \"PM10\", \"full_name\": \"Inhalable particulate matter (<10µm)\", \"value\": 6.93, \"units\": \"ug/m3\" }, \"Nitrogen dioxide\": { \"display_name\": \"NO2\", \"full_name\": \"Nitrogen dioxide\", \"value\": 6.61, \"units\": \"ppb\" }, \"Ozone\": { \"display_name\": \"O3\", \"full_name\": \"Ozone\", \"value\": 25.48, \"units\": \"ppb\" }, \"Sulfur dioxide\": { \"display_name\": \"SO2\", \"full_name\": \"Sulfur dioxide\", \"value\": 4.12, \"units\": \"ppb\" } }, \"date\": null }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(mock_response);

        System.out.println(root);

        mvc.perform(get("/api/current/" + location)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("location.coordinates.latitude", is(root.path("location").path("coordinates").get("latitude"))));

    }


}