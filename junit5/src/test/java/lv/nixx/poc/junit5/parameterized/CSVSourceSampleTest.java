package lv.nixx.poc.junit5.parameterized;

import lv.nixx.poc.junit5.service.Calculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;

class CSVSourceSampleTest {

    private Calculator calculator = new Calculator();
    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    @ParameterizedTest(name = "Line:{index} a={0} b={1} expected={2}")
    @CsvSource({
            "1,2,3",
            "2,2,2+2",
            "3,4,9"
    })
    void calculatorAddTest(ArgumentsAccessor argumentsAccessor) throws ScriptException {
        final int a = argumentsAccessor.getInteger(0);
        final int b = argumentsAccessor.getInteger(1);
        final Integer result = eval(argumentsAccessor.getString(2));

        assertEquals("Incorrect result for params, a=" + a + " b=" + b, result, calculator.add(a, b));
    }

    @ParameterizedTest(name = "Line:{index} a={0} b={1} expected={2}")
    @CsvFileSource(resources = "/calculator_test_source_data.csv")
    void testWithTestDataFromFile(ArgumentsAccessor argumentsAccessor) {
        final int a = argumentsAccessor.getInteger(0);
        final int b = argumentsAccessor.getInteger(1);
        final Integer result = argumentsAccessor.getInteger(2);

        assertEquals("Incorrect result for params, a=" + a + " b=" + b, result, calculator.add(a, b));
    }

    private Integer eval(String expr) throws ScriptException {
            return (Integer)engine.eval(expr);
    }

}
