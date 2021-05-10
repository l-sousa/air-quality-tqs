package tqs.assignment.airquality.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.assignment.airquality.AirQualityApplication;
import tqs.assignment.airquality.entities.*;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class AirPollutionReportService {
    private static final String BM_API_KEY = "46bf63cb87f44e94bbe4bcd4c429c874";
    private static final String BM_API_URL = "https://api.breezometer.com/air-quality/v2/current-conditions?key=" + BM_API_KEY;

    private Map<Location, Cache> cacheStatsMap = new Hashtable<>();
    private RestTemplate restTemplate;

    public AirQuality getDataByLocation(String location) throws MalformedURLException, JsonProcessingException {

        Location l = new Location(location);

        Coordinates loc_coords = l.getCoordinates(); // GET LOCATION'S COORDINATES


        URL url = new URL(BM_API_URL + String.format("&lat=%f&lon=%f&features=pollutants_concentrations", loc_coords.getLatitude(), loc_coords.getLongitude()));

        restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);


        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());


        AirQuality air_quality = new AirQuality();

        air_quality.setLocation(l);
        for (JsonNode pollutant : root.path("data").path("pollutants")) {
            JsonNode concentration = pollutant.get("concentration");
            Pollutant p = new Pollutant(pollutant.get("display_name").asText(), pollutant.get("full_name").asText(), concentration.get("value").asDouble(), concentration.get("units").asText());

            air_quality.addPollutant(p);
        }
        return air_quality;
    }


}