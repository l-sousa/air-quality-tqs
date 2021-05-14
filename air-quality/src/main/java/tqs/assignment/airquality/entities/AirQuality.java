package tqs.assignment.airquality.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


@EqualsAndHashCode
@ToString
@Getter
@Setter
public class AirQuality {
    private Location location;
    private Map<String, Pollutant> pollutants = new TreeMap<>();
    private Date date = null;

    public AirQuality() {
    }

    public AirQuality(Location location) {
        this.location = location;
    }

    public void addPollutant(Pollutant p) {
        pollutants.put(p.getFull_name(), p);
    }


}
