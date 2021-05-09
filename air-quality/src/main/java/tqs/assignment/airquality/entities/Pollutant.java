package tqs.assignment.airquality.entities;


import java.util.Objects;

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

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pollutant pollutant = (Pollutant) o;
        return Double.compare(pollutant.value, value) == 0 && Objects.equals(display_name, pollutant.display_name) && Objects.equals(full_name, pollutant.full_name) && Objects.equals(units, pollutant.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(display_name, full_name, value, units);
    }

    @Override
    public String toString() {
        return "Pollutant{" +
                "display_name='" + display_name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", value=" + value +
                ", units='" + units + '\'' +
                '}';
    }
}

