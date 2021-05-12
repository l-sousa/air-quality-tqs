package tqs.assignment.airquality.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Coordinates {
    private double latitude;

    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinates() {
    }

}
