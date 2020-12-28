package lv.nixx.poc.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import lv.nixx.poc.junit5.service.Calculator;

@DisplayName("Unit tests for Calculator class")
@RunWith(JUnitPlatform.class)
public class CalculatorTest {

    private final Calculator c = new Calculator();

    @Test
    @DisplayName("Test for all calculator operations")
    public void calculatorFullTest() {


        assertAll("Calculator Add tests",
                () -> assertEquals((Integer) 7, c.add(3, 4)),
                () -> assertEquals((Integer) 2, c.add(3, -1)),
                () -> assertThrows(IllegalArgumentException.class, () -> c.add(null, 1))
        );

        Executable[] ex = new Executable[]{
                () -> assertEquals((Integer) (-1), c.subtract(3, 4)),
                () -> assertEquals((Integer) (-1), c.subtract(-2, -1)),
                () -> assertEquals((Integer) 10, c.subtract(30, 20))
        };

        assertAll("Calculator Subtract tests", ex);

    }

    @DisplayName("Null parameter test")
    @Test
    public void nullParameterTest() {
        final IllegalArgumentException npe = assertThrows(IllegalArgumentException.class, () -> c.add(10, null));
        assertEquals("Argument can't be null", npe.getMessage());
    }


}
