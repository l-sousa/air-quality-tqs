package tqs.assignment.airquality.entities;

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

    public Location(String address) {
        this.address = address;
    }
}
