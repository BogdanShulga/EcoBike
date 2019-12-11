package model.regularBikes;

import model.RegularBike;

public class FoldingBike extends RegularBike {

    public FoldingBike(String brand,
                       int weight,
                       boolean lights,
                       String color,
                       int price,
                       int wheelSize,
                       int gearNumber) {

        super(brand, weight, lights, color, price, wheelSize, gearNumber);
    }

    @Override
    public String toString() {
        return "FOLDING BIKE "
                + getBrand()
                + " with "
                + getGearNumber()
                + " gear(s) and "
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
