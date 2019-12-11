package model.electricBikes;

import model.ElectricBike;

public class SpeedelecBike extends ElectricBike {

    public SpeedelecBike(String brand,
                         int weight,
                         boolean lights,
                         String color,
                         int price,
                         int maxSpeed,
                         int batteryCapacity) {

        super(brand, weight, lights, color, price, maxSpeed, batteryCapacity);
    }

    @Override
    public String toString() {
        return "SPEEDELEC "
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
