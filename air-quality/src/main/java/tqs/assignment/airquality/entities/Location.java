package tqs.assignment.airquality.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tqs.assignment.airquality.service.AddressToCoordinatesService;

import java.net.MalformedURLException;

@EqualsAndHashCode
@ToString
public class Location {

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private Coordinates coordinates;

    public Location(String address) throws MalformedURLException, JsonProcessingException {
        this.address = address;
        this.coordinates = AddressToCoordinatesService.getCoordinatesOfAddress(address);
    }



}
