package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserInputServiceTest {

    @Mock
    Scanner scannerMock;

    @InjectMocks
    UserInputService userInputService;

    @Test
    public void getStringUserInputTestWhenAnyStringUserInput() {

        when(scannerMock.nextLine()).thenReturn("any string 98798ui`'\\");

        assertEquals("any string 98798ui`'\\", userInputService.getStringUserInput(false));

        verify(scannerMock, times(1)).nextLine();
    }

    // Recursion expected. The test recognize just the fact that some unknown exception
    // would be thrown when user input is not valid
    //If exception will not be thrown, then test will completed successfully, but with error message
    @Test
    public void getStringUserInputTestWhenNoLineUserInput() throws InterruptedException {

        when(scannerMock.nextLine()).thenThrow(new NoSuchElementException());

        Thread thread = new Thread(() ->
                assertThrows(Exception.class, () -> userInputService.getStringUserInput(false))
        );

        thread.start();

        Thread.sleep(50);

        thread.interrupt();
    }

    // Recursion expected. The test recognize just the fact that some unknown exception
    // would be thrown when scanner closed
    //If exception will not be thrown, then test will completed successfully, but with error message
    @Test
    public void getStringUserInputTestWhenScannerClosed() throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        userInputService.setScanner(scanner);

        scanner.close();

        Thread thread = new Thread(() ->
                assertThrows(Exception.class, () -> userInputService.getStringUserInput(false))
        );

        thread.start();

        Thread.sleep(50);

        thread.interrupt();
    }

    @Test
    public void getStringUserInputTestWhenValidFileNameUserInput() {

        when(scannerMock.nextLine()).thenReturn("vaLid_file-name777");

        assertEquals("vaLid_file-name777", userInputService.getStringUserInput(true));

        verify(scannerMock, times(1)).nextLine();
    }

    // Recursion expected. The test recognize just the fact that some unknown exception
    // would be thrown when user input is not valid
    //If exception will not be thrown, then test will completed successfully, but with error message
    @Test
    public void getStringUserInputTestWhenNotValidFileNameUserInput() throws InterruptedException {

        when(scannerMock.nextLine()).thenReturn("not valid_file-name666;;?");

        Thread thread = new Thread(() ->
                assertThrows(Exception.class, () -> userInputService.getStringUserInput(true))
        );

        thread.start();

        Thread.sleep(50);

        thread.interrupt();
    }

    @Test
    public void getIntUserInputTestWhenValidUserInput() {

        when(scannerMock.nextLine()).thenReturn("137");

        assertEquals(137, userInputService.getIntUserInput());

        verify(scannerMock, times(1)).nextLine();
    }

    // Recursion expected. The test recognize just the fact that some unknown exception
    // would be thrown when user input is not valid
    //If exception will not be thrown, then test will completed successfully, but with error message
    @Test
    public void getIntUserInputTestWhenNotValidUserInputThrowException() throws InterruptedException {

        when(scannerMock.nextLine()).thenReturn("some string");

        Thread thread = new Thread(() ->
                assertThrows(Exception.class, () -> userInputService.getIntUserInput())
        );

        thread.start();

        Thread.sleep(50);

        thread.interrupt();
    }

    @Test
    public void getIntUserInputWithRangeTestWhenValidUserInput() {

        int[] neededUserInputArray = {1, 2, 3};

        for (int neededInt: neededUserInputArray) {

            when(scannerMock.nextLine()).thenReturn(String.valueOf(neededInt));

            assertEquals(neededInt, userInputService.getIntUserInput(neededUserInputArray));

        }
        verify(scannerMock, times(3)).nextLine();
    }

    // Recursion expected. The test recognize just the fact that some unknown exception
    // would be thrown when user input integer that not in needed range
    //If exception will not be thrown, then test will completed successfully, but with error message
    @Test
    public void getIntUserInputWithRangeTestWhenUserEnterIntButItNotInRange() throws InterruptedException {

        int[] neededUserInputArray = {1, 2, 3};

        when(scannerMock.nextLine()).thenReturn("5");

        Thread thread = new Thread(() ->
                assertThrows(Exception.class, () -> userInputService.getIntUserInput(neededUserInputArray))
        );

        thread.start();

        Thread.sleep(50);

        thread.interrupt();
    }
}