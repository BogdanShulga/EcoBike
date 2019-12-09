package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bike {

    private String brand;

    private int weight;

    private boolean isLights;

    private String color;

    private int price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bike)) return false;
        Bike bike = (Bike) o;
        return getWeight() == bike.getWeight() &&
                isLights() == bike.isLights() &&
                getPrice() == bike.getPrice() &&
                Objects.equals(getBrand(), bike.getBrand()) &&
                Objects.equals(getColor(), bike.getColor());
    }

    public String toTextFileString() {
        return "BIKE "
                + getBrand() + "; "
                + getWeight() + "; "
                + (isLights() ? "TRUE" : "FALSE") + "; "
                + getColor() + "; "
                + getPrice();
    }
}
