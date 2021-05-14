package tqs.assignment.airquality.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.assignment.airquality.entities.*;

import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@Service
@Configurable
public class AirPollutionReportService {
    public static final String BM_API_KEY = "54b27f99145c4a6691e919bb1601b69e";
    public static final String BM_API_URL_CURRENT = "https://api.breezometer.com/air-quality/v2/current-conditions?key=" + BM_API_KEY;
    public static final String BM_API_URL_FORECAST = "https://api.breezometer.com/air-quality/v2/forecast/hourly?key=" + BM_API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    public AirQuality getDataByLocation(Location location) throws JsonProcessingException, MalformedURLException {

        Coordinates loc_coords = location.getCoordinates();

        URL url = new URL(BM_API_URL_CURRENT + String.format("&lat=%f&lon=%f&features=pollutants_concentrations", loc_coords.getLatitude(), loc_coords.getLongitude()));

        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        AirQuality air_quality = new AirQuality();

        air_quality.setLocation(location);

        for (JsonNode pollutant : root.path("data").path("pollutants")) {

            JsonNode concentration = pollutant.get("concentration");
            Pollutant p = new Pollutant(pollutant.get("display_name").asText(), pollutant.get("full_name").asText(), concentration.get("value").asDouble(), concentration.get("units").asText());
            air_quality.addPollutant(p);

        }

        return air_quality;
    }

    public ArrayList<AirQuality> getForecastDataByLocation(Location location) throws MalformedURLException, JsonProcessingException, ParseException {

        Coordinates loc_coords = location.getCoordinates(); // GET LOCATION'S COORDINATES

        String result[] = getAPIDates();
        String iso_start_date = result[0];
        String iso_end_date = result[1];

        URL url = new URL(BM_API_URL_FORECAST + String.format("&lat=%f&lon=%f&features=pollutants_concentrations&start_datetime=%s&end_datetime=%s", loc_coords.getLatitude(), loc_coords.getLongitude(), iso_start_date, iso_end_date));

        ResponseEntity<String> response = restTemplate.getForEntity(url.toString(), String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset

        Set<String> set = new TreeSet<>();

        ArrayList<AirQuality> aq_list = new ArrayList<>();

        for (JsonNode j : root.path("data")) {
            String previous_date = j.get("datetime").asText();
            String d = df.format(iso.parse(previous_date));

            if (set.contains(d)) {
                continue;
            } else {

                set.add(d);
                AirQuality air_quality = new AirQuality();


                for (JsonNode pollutant : j.get("pollutants")) {

                    JsonNode concentration = pollutant.get("concentration");
                    Pollutant p = new Pollutant(pollutant.get("display_name").asText(), pollutant.get("full_name").asText(), concentration.get("value").asDouble(), concentration.get("units").asText());
                    air_quality.addPollutant(p);
                }

                air_quality.setLocation(location);
                air_quality.setDate(df.parse(d));
                aq_list.add(air_quality);
            }
        }
        return aq_list;

    }

    private String[] getAPIDates() throws ParseException {
        Date curr_date = new Date();

        DateFormat iso = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset

        String iso_current = iso.format(curr_date);

        Instant instant = Instant.parse(iso_current);

        LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));

        LocalDate starting_day = result.toLocalDate().plusDays(1);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String iso_start_date = iso.format(df.parse(starting_day.toString())).toString();

        LocalDate ending_day = starting_day.plusDays(2);

        String iso_end_date = iso.format(df.parse(ending_day.toString())).toString();

        return new String[]{ iso_start_date, iso_end_date };
    }
}