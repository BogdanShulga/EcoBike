package service;

import exception.NotValidFileNameException;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static service.Constants.ILLEGAL_CHARACTERS;

@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
//@RunWith(JUnitPlatform.class)
//@RunWith(PowerMockRunner.class)
//@PrepareForTest( UserInputService.class )
public class UserInputServiceTest {

    @Mock
    Scanner scannerMock;

    @Before
    public void setUp() {
        UserInputService.setScanner(scannerMock);
    }

    @Test
    public void getStringUserInputTestWithAnyStringUserInput() {

        when(scannerMock.nextLine()).thenReturn("any string 98798ui`'\\");

        assertEquals("any string 98798ui`'\\", UserInputService.getStringUserInput(false));

        verify(scannerMock, times(1)).nextLine();
    }

    @Test
    public void getStringUserInputTestWithNoLineUserInput() {

        when(scannerMock.nextLine()).thenThrow(new NoSuchElementException());

        Throwable thrown = assertThrows(NoSuchElementException.class,
                () -> UserInputService.getStringUserInput(false));

        assertNull(thrown.getMessage());

        verify(scannerMock, times(2)).nextLine();
    }

    @Test
    public void getStringUserInputTestWhenScannerClosed() {

        Scanner scanner = new Scanner(System.in);

        UserInputService.setScanner(scanner);

        scanner.close();

        Throwable thrown = assertThrows(IllegalStateException.class,
                () -> UserInputService.getStringUserInput(false));

        assertEquals("Scanner closed", thrown.getMessage());

        verify(scannerMock, never()).nextLine();
    }

    @Test
    public void getStringUserInputTestForValidFileNameUserInput() {

        when(scannerMock.nextLine()).thenReturn("vaLid_file-name777");

        assertEquals("vaLid_file-name777", UserInputService.getStringUserInput(true));

        verify(scannerMock, times(1)).nextLine();
    }

    // Tested separately from  getStringUserInput(true) to avoid recursion
    @Test
    public void ifNotValidFileNameThenThrowTestWhenNotValidString() {

        String validFileName = "valid_file_name";
        for (String s : ILLEGAL_CHARACTERS) {
            assertThrows(NotValidFileNameException.class,
                    () -> UserInputService.ifNotValidFileNameThenThrow(validFileName + s));
        }
    }



//    @Test
//    public void getIntUserInputTest() {
//    }
//
//    @Test
//    public void testGetIntUserInputTest() {
//    }
}