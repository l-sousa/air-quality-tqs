package tqs.assignment.airquality.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Location {
    private String address;
    private Coordinates coordinates;

    public Location(String address) throws JsonProcessingException {
        this.address = address;
    }
}
