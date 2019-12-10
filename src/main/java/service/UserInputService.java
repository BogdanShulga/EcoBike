package service;

import exception.NotValidFileNameException;
import exception.UserIntInputNotInRangeException;
import lombok.Setter;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static service.Constants.ILLEGAL_CHARACTERS;

@Setter
class UserInputService {

    private  Scanner scanner = new Scanner(System.in);

    public String getStringUserInput(boolean isFileName) {
        String userInput = "";
        boolean stop = false;
        while (!stop) {
            try {
                userInput = scanner.nextLine().trim();
                if (isFileName) ifNotValidFileNameThenThrow(userInput);
                stop = true;
            } catch (IllegalStateException | NoSuchElementException | NotValidFileNameException ex) {
                if (ex instanceof NotValidFileNameException) {
                    System.out.println("You entered not valid file name! Please try again!");
                } else {
                    System.out.println("You entered incorrect data! Please try again!");
                }
                scanner.nextLine(); // Added because the scanner is sometimes buggy
            }
        }
        return userInput;
    }

    private void ifNotValidFileNameThenThrow(String userInput) throws NotValidFileNameException {
        boolean isValidName = Arrays.stream(ILLEGAL_CHARACTERS).noneMatch(userInput::contains);
        if (!isValidName) {
            throw new NotValidFileNameException();
        }
    }

    public int getIntUserInput(int... validInput) {
        int userInput = 0;
        boolean stop = false;
        while (!stop) {
            try {
                userInput = Integer.parseInt(scanner.nextLine().trim());
                if (isIntInRange(userInput, validInput)) {
                    stop = true;
                } else {
                    throw new UserIntInputNotInRangeException();
                }
            } catch (IllegalStateException | NoSuchElementException | NumberFormatException | UserIntInputNotInRangeException ex) {
                System.out.println("You entered incorrect data! Please try again!");
                scanner.nextLine(); // Added because the scanner is sometimes buggy
            }
        }
        return userInput;
    }

    private boolean isIntInRange(int testInt, int... range) {

        boolean isIntInRange = false;

        for (int i : range) {
            if (i == testInt) {
                isIntInRange = true;
                break;
            }
        }
        return isIntInRange;
    }

    public int getIntUserInput() {
        int userInput = 0;
        boolean stop = false;
        while (!stop) {
            try {
                userInput = Integer.parseInt(scanner.nextLine().trim());
                stop = true;
            } catch (IllegalStateException | NoSuchElementException | NumberFormatException ex) {
                System.out.println("You entered incorrect data! Please try again!");
                scanner.nextLine(); // Added because the scanner is sometimes buggy
            }
        }
        return userInput;
    }

}
