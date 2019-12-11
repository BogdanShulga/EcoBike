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
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static service.Constants.*;

@Getter
@Setter
public class BikeService {

    private UserInputService userInputService = new UserInputService();

    private SpecialUserInputService specialUserInputService = new SpecialUserInputService();

    private List<Bike> foundBikes = new ArrayList<>();

    private volatile String unsuccessfulSearchResult = "";

    private List<Bike> bikesFromFile = new CopyOnWriteArrayList<>();

    public BikeService() {
        findAllTxtFilesInResourcesDir().forEach(this::readTxtFileToList);
        programFlowGuide();
    }

    private void programFlowGuide() {

        boolean stopProgram = false;

        System.out.println(ROOT_MESSAGE);

        int intUserInput = userInputService.getIntUserInput(1, 2, 3, 4, 5, 6, 7);

        if (intUserInput == 1) {
            showAllCatalog();
        } else if (intUserInput == 2) {
            addNewFoldingBikeToCatalog();
        } else if (intUserInput == 3) {
            addNewSpeedelecBikeToCatalog();
        } else if (intUserInput == 4) {
            addNewEBikeToCatalog();
        } else if (intUserInput == 5) {
            findFirstBikeByBrandMenuResolver();
        } else if (intUserInput == 6) {
            deleteAllFilesInResourcesDir();
            wrightCatalogToTxtFileWithCustomName();
        } else {
            System.out.println("Stopping the program!");
            stopProgram = true;
        }
        if (!stopProgram) programFlowGuide();
    }

    private List<File> findAllTxtFilesInResourcesDir() {

        List<File> resourceTxtFiles = new ArrayList<>();

        try {
            Files.walk(Paths.get(RESOURCES_DIR_PATH))
                    .filter(path -> path.toString().endsWith(".txt"))
                    .map(Path::toFile)
                    .forEach(resourceTxtFiles::add);
        } catch (IOException e) {
            System.out.println("The .txt files not found, because due to some error! The catalog is empty!");
        }
        return resourceTxtFiles;
    }

    private void readTxtFileToList(File txtFile) {

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

    private void showAllCatalog() {

        System.out.println("Here all bikes in catalog:\n");

        bikesFromFile.forEach(System.out::println);
    }

    private void addNewFoldingBikeToCatalog() {

        FoldingBike newFoldingBike = new FoldingBike(
                specialUserInputService.getBrandUserInput(),
                specialUserInputService.getWeightUserInput(),
                specialUserInputService.getLightsAvaliabilityUserInput(),
                specialUserInputService.getColorUserInput(),
                specialUserInputService.getPriceUserInput(),
                specialUserInputService.getWheelSizeUserInput(),
                specialUserInputService.getGearNumberUserInput());

        saveOrNotBikeAndPrintMessageWhenBikeAlreadyContainsOrNot(newFoldingBike);
    }

    private void addNewSpeedelecBikeToCatalog() {

        SpeedelecBike newSpeedelecBike = new SpeedelecBike(
                specialUserInputService.getBrandUserInput(),
                specialUserInputService.getWeightUserInput(),
                specialUserInputService.getLightsAvaliabilityUserInput(),
                specialUserInputService.getColorUserInput(),
                specialUserInputService.getPriceUserInput(),
                specialUserInputService.getMaxSpeedUserInput(),
                specialUserInputService.getBatteryCapacityUserInput());

        saveOrNotBikeAndPrintMessageWhenBikeAlreadyContainsOrNot(newSpeedelecBike);
    }

    private void addNewEBikeToCatalog() {

        EBike newEBike = new EBike(
                specialUserInputService.getBrandUserInput(),
                specialUserInputService.getWeightUserInput(),
                specialUserInputService.getLightsAvaliabilityUserInput(),
                specialUserInputService.getColorUserInput(),
                specialUserInputService.getPriceUserInput(),
                specialUserInputService.getMaxSpeedUserInput(),
                specialUserInputService.getBatteryCapacityUserInput());

        saveOrNotBikeAndPrintMessageWhenBikeAlreadyContainsOrNot(newEBike);
    }

    private void saveOrNotBikeAndPrintMessageWhenBikeAlreadyContainsOrNot(Bike newBike) {

        boolean isContains = bikesFromFile.contains(newBike);

        if (isContains) {
            System.out.println("The new bike, shown below, not added, because it is already present in the catalog!");
        } else {
            new Thread(() -> bikesFromFile.add(newBike)).start();
            System.out.println("The new bike will be added to the catalog as soon as it will be possible!");
        }
    }

    private void findFirstBikeByBrandMenuResolver() {

        System.out.println(FIND_FIRST_BIKE_BY_BRAND_MESSAGE);

        int intUserInput1 = userInputService.getIntUserInput(1, 2);

        if (intUserInput1 == 1) {
            printInfoAboutFoundBikes();
        } else {
            Class<? extends Bike> bikeTypeUserInput = specialUserInputService.getBikeTypeUserInput();
            String brandUserInput = specialUserInputService.getBrandUserInput();
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
            unsuccessfulSearchResult = String.format("%s of %s brand not found!", bikeTypeName, brand);
        }
    }

    private void deleteAllFilesInResourcesDir() {
        try {
            Files.walk(Paths.get(RESOURCES_DIR_PATH))
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void wrightCatalogToTxtFileWithCustomName() {

        boolean success = true;

        String newFileName = specialUserInputService.getFileNameUserInput();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.format(FILE_PATH, newFileName)))) {
            for (Bike bike : bikesFromFile) {
                writer.write(bike.toTextFileString() + System.lineSeparator());
            }
        } catch (IOException e) {
            success = false;
            System.out.println(String.format("All data NOT written to the file: %s.txt, " +
                    "due to some error!", newFileName));
        }
        if (success) System.out.println(String.format("All data written to the file: %s.txt", newFileName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BikeService that = (BikeService) o;
        return Objects.equals(getUserInputService(), that.getUserInputService()) &&
                Objects.equals(getSpecialUserInputService(), that.getSpecialUserInputService()) &&
                Objects.equals(getFoundBikes(), that.getFoundBikes()) &&
                Objects.equals(getUnsuccessfulSearchResult(), that.getUnsuccessfulSearchResult()) &&
                Objects.equals(getBikesFromFile(), that.getBikesFromFile());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getUserInputService(), getSpecialUserInputService(), getFoundBikes(), getUnsuccessfulSearchResult(), getBikesFromFile());
    }
}
