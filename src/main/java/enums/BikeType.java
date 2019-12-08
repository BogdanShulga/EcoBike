package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BikeType {
    EBike("E-BIKE"), SpeedelecBike("SPEEDELEC BIKE"), FoldingBike("FOLDING BIKE");

    String bikeType;
}
