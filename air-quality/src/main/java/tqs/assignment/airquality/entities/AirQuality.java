package tqs.assignment.airquality.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;


@EqualsAndHashCode
@ToString
public class AirQuality {

    @Getter
    @Setter
    private Location location;

    @Getter
    @Setter
    private Map<String, Pollutant> pollutants = new TreeMap<>();


    public AirQuality() {
    }

    public AirQuality(Location location) {
        this.location = location;
    }

    public void addPollutant(Pollutant p) {
        pollutants.put(p.getFull_name(), p);
    }


}
