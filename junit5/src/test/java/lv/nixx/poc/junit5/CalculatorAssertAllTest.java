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
public class CalculatorAssertAllTest {

    @Test
    @DisplayName("Test for all calculator operations")
    public void calculatorFullTest() {

        final Calculator c = new Calculator();

        assertAll("Calculator Add tests",
                () -> assertEquals((Integer) 7, c.add(3, 4)),
                () -> assertEquals((Integer) 2, c.add(3, -1)),
                () -> assertThrows(NullPointerException.class, () -> c.add(null, 1))
        );

        Executable[] ex = new Executable[]{
                () -> assertEquals((Integer) (-1), c.subtract(3, 4)),
                () -> assertEquals((Integer) (-1), c.subtract(-2, -1)),
                () -> assertEquals((Integer) 10, c.subtract(30, 20))
        };

        assertAll("Calculator Subtract tests", ex);

    }


}
