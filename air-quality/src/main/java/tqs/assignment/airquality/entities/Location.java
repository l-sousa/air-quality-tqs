package tqs.assignment.airquality.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import tqs.assignment.airquality.service.AddressToCoordinatesService;

import java.net.MalformedURLException;

import java.util.Objects;

public class Location {

    private String address;

    private Coordinates coordinates;

    public Location(String address) throws MalformedURLException, JsonProcessingException {
        this.address = address;
        this.coordinates = AddressToCoordinatesService.getCoordinatesOfAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(address, location.address) && Objects.equals(coordinates, location.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, coordinates);
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
