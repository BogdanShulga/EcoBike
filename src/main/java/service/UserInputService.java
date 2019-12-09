package service;

import exception.NotValidFileNameException;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static service.Constants.ILLEGAL_CHARACTERS;


class UserInputService {

    private static Scanner scanner = new Scanner(System.in);

    // this method only for tests
    public static void setScanner(Scanner scanner) {
        UserInputService.scanner = scanner;
    }

    // this method only for tests
    public static Scanner getScanner() {
        return scanner;
    }

    static String getStringUserInput(boolean isFileName) {
        String userInput = "";
        boolean stop = false;
        while (!stop) {
            try {
                userInput = scanner.nextLine().trim();
                if (isFileName) ifNotValidFileNameThenThrow(userInput);
            } catch (IllegalStateException | NoSuchElementException | NotValidFileNameException ex) {
                if (ex instanceof NotValidFileNameException) {
                    System.out.println("You entered not valid file name! Please try again!");
                } else {
                    System.out.println("You entered incorrect data! Please try again!");
                }
                scanner.nextLine(); // Added because the scanner is sometimes buggy
                continue;
            }
            stop = true;
        }
        return userInput;
    }

    static void ifNotValidFileNameThenThrow(String userInput) throws NotValidFileNameException {
        boolean isValidName = Arrays.stream(ILLEGAL_CHARACTERS).noneMatch(userInput::contains);
        if (!isValidName) {
            throw new NotValidFileNameException();
        }
    }

    static int getIntUserInput(int... validInput) {
        int userInput = 0;
        boolean stop = false;
        while (!stop) {
            try {
                userInput = Integer.parseInt(scanner.nextLine().trim());
            } catch (IllegalStateException | NoSuchElementException | NumberFormatException ex) {
                System.out.println("You entered incorrect data! Please try again!");
                scanner.nextLine(); // Added because the scanner is sometimes buggy
                continue;
            }
            boolean good = true;
            for (int i : validInput) {
                if (i == userInput) {
                    good = false;
                    break;
                }
            }
            if (!good) {
                stop = true;
            } else {
                System.out.println("You entered incorrect data! Please try again!");
            }
        }
        return userInput;
    }

    static int getIntUserInput() {
        int userInput = 0;
        boolean stop = false;
        while (!stop) {
            try {
                userInput = Integer.parseInt(scanner.nextLine().trim());
            } catch (IllegalStateException | NoSuchElementException | NumberFormatException ex) {
                System.out.println("You entered incorrect data! Please try again!");
                scanner.nextLine(); // Added because the scanner is sometimes buggy
                continue;
            }
            stop = true;
        }
        return userInput;
    }

}
