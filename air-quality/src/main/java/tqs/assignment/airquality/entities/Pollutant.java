package tqs.assignment.airquality.entities;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Pollutant {
    private String display_name;
    private String full_name;
    private double value;
    private String units;

    public Pollutant(String display_name, String full_name, double value, String units) {
        this.display_name = display_name;
        this.full_name = full_name;
        this.value = value;
        this.units = units;
    }
}

