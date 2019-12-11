package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class RegularBike extends Bike {

    private int wheelSize;

    private int gearNumber;

    public RegularBike(String brand,
                       int weight,
                       boolean lights,
                       String color,
                       int price,
                       int wheelSize,
                       int gearNumber) {

        super(brand, weight, lights, color, price);

        this.wheelSize = wheelSize;

        this.gearNumber = gearNumber;
    }

    @Override
    public String toTextFileString() {
        return "FOLDING BIKE "
                + getBrand() + "; "
                + getWheelSize() + "; "
                + getGearNumber() + "; "
                + getWeight() + "; "
                + (isLights()? "TRUE" : "FALSE") + "; "
                + getColor() + "; "
                + getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegularBike)) return false;
        if (!super.equals(o)) return false;
        RegularBike that = (RegularBike) o;
        return getWheelSize() == that.getWheelSize() &&
                getGearNumber() == that.getGearNumber();
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getWheelSize(), getGearNumber());
    }
}
