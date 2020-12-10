package lv.nixx.poc.junit5.pbt;

import net.jqwik.api.*;

public class CustomClassTest {

    @Example
    public boolean example1() {
        return 1 + 2 ==3;
    }

    @Example
    public boolean example2() {
        return 1 + 2 ==4;
    }

    @Provide
    private Arbitrary<String> createTestValues() {
        return Arbitraries.of("A", "B", "C", "D");
    }

    @Property
    @Report(Reporting.GENERATED)
    boolean checkProcessString(@ForAll("createTestValues") String input) {
        ClassToTest classToTest = new ClassToTest();
        return classToTest.process(input).equals(input + ".output");
    }

    class ClassToTest {
        String process(String input) {
            System.out.println("Process:" + input);
            if (input.equalsIgnoreCase("D")) {
                throw new IllegalArgumentException("Input value 'D' is not valid");
            }
            return input + ".output";
        }
    }

}
