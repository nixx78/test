package lv.nixx.poc.junit5.pbt;

import net.jqwik.api.*;

import static org.junit.Assert.assertEquals;

@Label("Property based testing sandbox")
class JqwikSandbox {

    @Data
    Iterable<Tuple.Tuple2<String, String>> testData() {
        return Table.of(
                Tuple.of("A", "A.result"),
                Tuple.of("B", "B.result"),
                Tuple.of("C", "C.result")
        );
    }

    @Property
    @FromData("testData")
    @Label("test data sample")
    @Report(Reporting.GENERATED)
    void testDataUsageSampleTest(@ForAll String input, @ForAll String expectedResult) {
        System.out.println("Index: " + input + " Result: " + expectedResult);
        assertEquals(expectedResult, process(input));
    }

    @Provide("2 to 8")
    Arbitrary<Integer>  getNumbers() {
        return Arbitraries.integers().between(2, 8);
    }

    @Property
    boolean numbersTest(@ForAll("2 to 8") Integer i) {
        System.out.println("Input number:" + i);
        return i > 0;
    }

    private String process(String input) {
        return input + ".result";
    }

}
