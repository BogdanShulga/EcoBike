package model.electricBikes;

import model.ElectricBike;

public class EBike extends ElectricBike {

    public EBike(String brand,
                 int weight,
                 boolean lights,
                 String color,
                 int price,
                 int maxSpeed,
                 int batteryCapacity) {

        super(brand, weight, lights, color, price, maxSpeed, batteryCapacity);
    }

    @Override
    public String toTextFileString() {
        return super.toTextFileString();
    }

    @Override
    public String toString() {
        return "E-BIKE "
                + getBrand()
                + " with "
                + getBatteryCapacity()
                + " mAh battery and "
                + (isLights()? "" : "no")
                + "head/tail light.\n"
                + "Price: "
                + getPrice()
                + " euros.\n";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
