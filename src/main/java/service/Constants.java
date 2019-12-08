package service;

public interface Constants {

    String FILE_PATH = "src/main/resources/%s.txt";

    String RESOURCES_DIR_PATH = "src/main/resources/";

    int FOLDING_BRSND_START_INDEX = "FOLDING BIKE ".length();

    int SPEEDELEC_BRAND_START_INDEX = "SPEEDELEC ".length();

    int E_BIKE_BRAND_START_INDEX = "E-BIKE ".length();

    String[] ILLEGAL_CHARACTERS = { "/", "\n", "\r", "\t", "\0", "\f", "`", "?", "*", "\\", "<", ">", "|", "\"", ":" };

    String ROOT_MESSAGE =
            "========================================================================\n" +
            "Please, make your choice:\n" +
            "1 – Show the entire EcoBike catalog\n" +
            "2 – Add a new folding bike\n" +
            "3 – Add a new speedelec\n" +
            "4 – Add a new e-bike\n" +
            "5 – Find the first item of a particular brand\n" +
            "6 – Write to file\n" +
            "7 – Stop the program";

    String CHOOSE_BIKE_TYPE_MESSAGE =
            "========================================================================\n" +
            "Please, chose a bike type:\n" +
            "1 – FOLDING BIKE\n" +
            "2 – SPEEDELEC BIKE\n" +
            "3 – E-BIKE";

    String FIND_BIKE_MESSAGE =
            "========================================================================\n" +
            "Please, make your choice:\n" +
            "1 – Show earlier found bike(s)\n" +
            "    (you may not see the results of the last search if it has not yet completed)\n" +
            "2 – Find another bike";
}
