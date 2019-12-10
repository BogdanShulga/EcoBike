package service;

import model.Bike;
import model.electricBikes.EBike;
import model.electricBikes.SpeedelecBike;
import model.regularBikes.FoldingBike;

import static service.Constants.CHOOSE_BIKE_TYPE_MESSAGE;

class SpecialUserInputService {

    private UserInputService userInputService = new UserInputService();

    String getBrandUserInput() {
        System.out.println("Please enter the bike brand:");
        return userInputService.getStringUserInput(false);
    }

    int getWheelSizeUserInput() {
        System.out.println("Please enter the size of the wheels (in inch):");
        return userInputService.getIntUserInput();
    }

    int getGearNumberUserInput() {
        System.out.println("Please enter the number of gears:");
        return userInputService.getIntUserInput();
    }

    int getWeightUserInput() {
        System.out.println("Please enter the weight of the bike (in grams):");
        return userInputService.getIntUserInput();
    }

    boolean getLightsAvaliabilityUserInput() {
        System.out.println("Please enter the availability of lights at front and back:\n" +
                "enter 1 if lights will be available\n" +
                "enter 2 if not");
        int intUserInput = userInputService.getIntUserInput(1, 2);
        return intUserInput == 1;
    }

    String getColorUserInput() {
        System.out.println("Please enter the color:");
        return userInputService.getStringUserInput(false);
    }

    int getPriceUserInput() {
        System.out.println("Please enter the price (in euros):");
        return userInputService.getIntUserInput();
    }

    int getMaxSpeedUserInput() {
        System.out.println("Please enter the maximum speed (in km/h):");
        return userInputService.getIntUserInput();
    }

    int getBatteryCapacityUserInput() {
        System.out.println("Please enter the battery capacity (in mAh):");
        return userInputService.getIntUserInput();
    }

    String getFileNameUserInput() {
        System.out.println("Please, enter the file name, in which all data will be written\n" +
                "(don't use this characters: " +
                "'/', '\\n', '\\r', '\\t', '\\0', '\\f', '`', '?', '*', '\\\\', '<', '>', '|', '\\\"', ':')");
        return userInputService.getStringUserInput(true);
    }

    Class<? extends Bike> getBikeTypeUserInput() {

        System.out.println(CHOOSE_BIKE_TYPE_MESSAGE);

        int intUserInput = userInputService.getIntUserInput(1, 2, 3);

        Class<? extends Bike> chosenBikeClass;

        if (intUserInput == 1) {
            chosenBikeClass = FoldingBike.class;
        } else if (intUserInput == 2) {
            chosenBikeClass = SpeedelecBike.class;
        } else {
            chosenBikeClass = EBike.class;
        }
        return chosenBikeClass;
    }
}
