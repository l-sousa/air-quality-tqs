package tqs.assignment.airquality.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.assignment.airquality.entities.Coordinates;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@Configurable
public class AddressToCoordinatesService {
    public static final String PS_API_KEY = "7384c940f120ca0a135cb8e1c3afe6e1";
    public static final String PS_API_URL = "http://api.positionstack.com/v1/forward?access_key=" + PS_API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    public Coordinates getCoordinatesOfAddress(String address) throws JsonProcessingException, MalformedURLException {
        URL url = new URL(PS_API_URL + String.format("&query=%s", address));

        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response.getBody());

        return new Coordinates(root.path("data").get(0).path("latitude").asDouble(), root.path("data").get(0).path("longitude").asDouble());

    }

}
