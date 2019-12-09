package service;

import enums.BikeType;
import lombok.Getter;
import lombok.Setter;
import model.Bike;
import model.electricBikes.EBike;
import model.electricBikes.SpeedelecBike;
import model.regularBikes.FoldingBike;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static service.SpecialUserInputService.*;
import static service.Constants.*;
import static service.UserInputService.getIntUserInput;

@Getter
@Setter
public class BikeService {

    private List<Bike> foundBikes = new ArrayList<>();

    private String unsuccessfulSearchResult = "";

    private List<Bike> bikesFromFile = new CopyOnWriteArrayList<>();

    public BikeService() {
        findTxtFiles().forEach(this::readFile);
        programFlowGuide();
    }

    private void programFlowGuide() {

        boolean stopProgram = false;

        System.out.println(ROOT_MESSAGE);

        int intUserInput = getIntUserInput(1, 2, 3, 4, 5, 6, 7);

        if (intUserInput == 1) {
            showCatalog();
        } else if (intUserInput == 2) {
            addNewFoldingBike();
        } else if (intUserInput == 3) {
            addNewSpeedelecBike();
        } else if (intUserInput == 4) {
            addNewEBike();
        } else if (intUserInput == 5) {
            findFirstBikeByBrand();
        } else if (intUserInput == 6) {
            wrightToFile();
        } else {
            System.out.println("Stopping the program!");
            stopProgram = true;
        }
        if (!stopProgram) programFlowGuide();
    }

    private List<File> findTxtFiles() {

        List<File> resourceTxtFiles = new ArrayList<>();

        try {
            Files.walk(Paths.get(RESOURCES_DIR_PATH))
                    .filter(path -> path.toString().endsWith(".txt"))
                    .map(Path::toFile)
                    .forEach(resourceTxtFiles::add);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return resourceTxtFiles;
    }

    private void readFile(File txtFile) {

        try (BufferedReader br = new BufferedReader(new FileReader(txtFile))) {

            String st;

            while ((st = br.readLine()) != null) {

                String[] properties = st.split("; ");

                if (properties[0].startsWith("FOLDING BIKE")) {

                    bikesFromFile.add(new FoldingBike(
                            properties[0].substring(FOLDING_BRSND_START_INDEX), // brand
                            Integer.parseInt(properties[3]),                    // weight
                            Boolean.parseBoolean(properties[4]),                // lights
                            properties[5],                                      // color
                            Integer.parseInt(properties[6]),                    // price
                            Integer.parseInt(properties[1]),                    // wheelSize
                            Integer.parseInt(properties[2])));                  // gearNumber

                } else if (properties[0].startsWith("E-BIKE")) {

                    bikesFromFile.add(new EBike(
                            properties[0].substring(E_BIKE_BRAND_START_INDEX),  // brand
                            Integer.parseInt(properties[2]),                    // weight
                            Boolean.parseBoolean(properties[3]),                // lights
                            properties[5],                                      // color
                            Integer.parseInt(properties[6]),                    // price
                            Integer.parseInt(properties[1]),                    // maxSpeed
                            Integer.parseInt(properties[4])));                  // batteryCapacity

                } else if (properties[0].startsWith("SPEEDELEC")) {

                    bikesFromFile.add(new SpeedelecBike(
                            properties[0].substring(SPEEDELEC_BRAND_START_INDEX), // brand
                            Integer.parseInt(properties[2]),                      // weight
                            Boolean.parseBoolean(properties[3]),                  // lights
                            properties[5],                                        // color
                            Integer.parseInt(properties[6]),                      // price
                            Integer.parseInt(properties[1]),                      // maxSpeed
                            Integer.parseInt(properties[4])));                    // batteryCapacity
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showCatalog() {

        System.out.println("Here all bikes in catalog:\n");

        bikesFromFile.forEach(System.out::println);
    }

    private void addNewFoldingBike() {

        FoldingBike newFoldingBike = new FoldingBike(
                getBrandUserInput(),
                getWeightUserInput(),
                getLightsAvaliabilityUserInput(),
                getColorUserInput(),
                getPriceUserInput(),
                getWheelSizeUserInput(),
                getGearNumberUserInput());

        saveOrNotBikeAndPrintMessageWhenBikeAlreadyContainsOrNot(newFoldingBike);
    }

    private void addNewSpeedelecBike() {

        SpeedelecBike newSpeedelecBike = new SpeedelecBike(
                getBrandUserInput(),
                getWeightUserInput(),
                getLightsAvaliabilityUserInput(),
                getColorUserInput(),
                getPriceUserInput(),
                getMaxSpeedUserInput(),
                getBatteryCapacityUserInput());

        saveOrNotBikeAndPrintMessageWhenBikeAlreadyContainsOrNot(newSpeedelecBike);
    }

    private void addNewEBike() {

        EBike newEBike = new EBike(
                getBrandUserInput(),
                getWeightUserInput(),
                getLightsAvaliabilityUserInput(),
                getColorUserInput(),
                getPriceUserInput(),
                getMaxSpeedUserInput(),
                getBatteryCapacityUserInput());

        saveOrNotBikeAndPrintMessageWhenBikeAlreadyContainsOrNot(newEBike);
    }

    private void saveOrNotBikeAndPrintMessageWhenBikeAlreadyContainsOrNot(Bike newBike) {

        boolean isContains = bikesFromFile.contains(newBike);

        if (isContains) {
            System.out.println("The new bike, shown below, has not been added, because it is already present in the catalog!");
        } else {
            System.out.println("The new bike will be added to the catalog as soon as it will be possible!");
            new Thread(() -> bikesFromFile.add(newBike)).start();
        }
    }

    private void findFirstBikeByBrand() {

        System.out.println(FIND_BIKE_MESSAGE);

        int intUserInput1 = getIntUserInput(1, 2);

        if (intUserInput1 == 1) {
            printInfoAboutFoundBikes();
        } else {
            Class<? extends Bike> bikeTypeUserInput = getBikeTypeUserInput();
            String brandUserInput = getBrandUserInput();
            new Thread(() -> findFirstBikeByBrand(
                    brandUserInput,
                    bikeTypeUserInput,
                    this))
                    .start();
            System.out.println("Searching is started! You will be able to see result later in item 5 of root menu.");
        }
    }

    private void printInfoAboutFoundBikes() {
        if (foundBikes.isEmpty()) {
            System.out.println("There is no earlier found bike(s)!");
        } else {
            System.out.println("Earlier found bike(s):\n");
            foundBikes.forEach(System.out::println);
        }
        if (!unsuccessfulSearchResult.isEmpty()) System.out.println(unsuccessfulSearchResult);
    }

    private void findFirstBikeByBrand(String brand,
                                      Class<? extends Bike> bikeClass,
                                      BikeService bikeService) {

        Optional<Bike> optionalFoundBike = bikesFromFile.stream()
                .filter(bike -> bike.getBrand().equalsIgnoreCase(brand)
                        && bike.getClass().getName().equals(bikeClass.getName()))
                .findFirst();

        if (optionalFoundBike.isPresent()) {
            bikeService.getFoundBikes().add(optionalFoundBike.get());
            unsuccessfulSearchResult = "";
        } else {
            //Just to show in message bike type names like: E-BIKE, SPEEDELEC BIKE, FOLDING BIKE
            String bikeTypeName = BikeType.valueOf(bikeClass.getSimpleName()).getBikeType();
            unsuccessfulSearchResult = String.format("%s of %s brand is not found!", bikeTypeName, brand);
        }
    }

    private void wrightToFile() {

        String newFileName = getFileNameUserInput();

        try {
            Files.walk(Paths.get(RESOURCES_DIR_PATH))
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format(FILE_PATH, newFileName)))) {
            for (Bike bike : bikesFromFile) {
                writer.write(bike.toTextFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(String.format("All data has been written to the file: %s.txt", newFileName));
    }
}
