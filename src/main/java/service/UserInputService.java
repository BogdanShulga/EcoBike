package service;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static service.Constants.ILLEGAL_CHARACTERS;

public class UserInputService {

    private static Scanner scanner = new Scanner(System.in);

    static String getStringUserInput(boolean isFileName) {
        String userInput = "";
        boolean stop = false;
        while (!stop) {
            try {
                userInput = scanner.nextLine().trim();
                if (isFileName) {
                    boolean isValidName = Arrays.stream(ILLEGAL_CHARACTERS).noneMatch(userInput::contains);
                    if (!isValidName) {
                        System.out.println("You enter incorrect data! Please try again!");
                        continue;
                    }
                }
            } catch (IllegalStateException | NoSuchElementException ex) {
                System.out.println("You enter incorrect data! Please try again!");
                continue;
            }
            stop = true;
        }
        return userInput;
    }

    static int getIntUserInput(int... validInput) {
        int userInput = 0;
        boolean stop = false;
        while (!stop) {
            try {
                userInput = Integer.parseInt(scanner.nextLine().trim());
            } catch (IllegalStateException | NoSuchElementException | NumberFormatException ex) {
                System.out.println("You enter incorrect data! Please try again!");
                scanner.nextLine();
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
                System.out.println("You enter incorrect data! Please try again!!!!!!!!");
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
                System.out.println("You enter incorrect data! Please try again!");
                continue;
            }
            stop = true;
        }
        return userInput;
    }

}
