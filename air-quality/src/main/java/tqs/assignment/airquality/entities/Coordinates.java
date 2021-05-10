package tqs.assignment.airquality.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@EqualsAndHashCode
@ToString
public class Coordinates {
    @Getter
    @Setter
    private double latitude;

    @Getter
    @Setter
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinates() {
    }

}
