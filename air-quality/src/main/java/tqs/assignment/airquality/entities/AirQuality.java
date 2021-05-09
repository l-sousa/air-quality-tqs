package tqs.assignment.airquality.entities;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AirQuality {

    private Location location;

    private Map<String, Pollutant> pollutants = new HashMap<>();


    public AirQuality() {

    }

    public void addPollutant(Pollutant p) {
        pollutants.put(p.getFull_name(), p);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Map<String, Pollutant> getPollutants() {
        return pollutants;
    }

    public void setPollutants(Map<String, Pollutant> pollutants) {
        this.pollutants = pollutants;
    }

    @Override
    public String toString() {
        return "AirQuality{" +
                "location=" + location +
                ", pollutants=" + pollutants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirQuality that = (AirQuality) o;
        return Objects.equals(location, that.location) && Objects.equals(pollutants, that.pollutants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, pollutants);
    }
}
