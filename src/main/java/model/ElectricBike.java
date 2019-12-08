package model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElectricBike extends Bike {

    private int maxSpeed;

    private int batteryCapacity;

    public ElectricBike(String brand,
                        int weight,
                        boolean lights,
                        String color,
                        int price,
                        int maxSpeed,
                        int batteryCapacity) {

        super(brand, weight, lights, color, price);

        this.maxSpeed = maxSpeed;

        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public String toTextFileString() {
        return "E-BIKE "
                + getBrand() + "; "
                + getMaxSpeed() + "; "
                + getWeight() + "; "
                + (isLights() ? "TRUE" : "FALSE") + "; "
                + getBatteryCapacity() + "; "
                + getColor() + "; "
                + getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElectricBike)) return false;
        if (!super.equals(o)) return false;
        ElectricBike that = (ElectricBike) o;
        return getMaxSpeed() == that.getMaxSpeed() &&
                getBatteryCapacity() == that.getBatteryCapacity();
    }
}
