package lv.nixx.poc.junit5.parameterized;

import lv.nixx.poc.junit5.service.Calculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVSourceSampleTest {

    //TODO https://junit.org/junit5/docs/current/user-guide/#overview

    private final Calculator calculator = new Calculator();

    @ParameterizedTest(name = "Line:{index} a={0} b={1} expected={2}")
    @CsvSource({
            "1,2,3",
            "2,2,4",
            "3,4,9"
    })
    void calculatorAddTest(ArgumentsAccessor argumentsAccessor) {
        final int a = argumentsAccessor.getInteger(0);
        final int b = argumentsAccessor.getInteger(1);
        final Integer expect = argumentsAccessor.getInteger(2);

        assertEquals(expect, calculator.add(a, b), "Incorrect result for params, a=" + a + " b=" + b);
    }

    @ParameterizedTest(name = "Line:{index} a={0} b={1} expected={2}")
    @CsvFileSource(resources = "/calculator_test_source_data.csv")
    void testWithTestDataFromFile(ArgumentsAccessor argumentsAccessor) {
        final int a = argumentsAccessor.getInteger(0);
        final int b = argumentsAccessor.getInteger(1);
        final Integer result = argumentsAccessor.getInteger(2);

        assertEquals(result, calculator.add(a, b), "Incorrect result for params, a=" + a + " b=" + b);
    }

}
