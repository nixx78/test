package lv.nixx.poc.junit5;


import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JUnitClassicTest {

    @Test
    void failedTest() {
        fail("JUnit Fail message");
    }

    @Test
    void successTest() {
        assertEquals(1, 1);
    }

}
