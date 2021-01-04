package lv.nixx.poc.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Junit5Sandbox {

    @Test
    void testFailWithTimeout()  {
        Assertions.assertTimeout(Duration.ofMillis(200), () -> Thread.sleep(100));
    }

    @Test
    void assumptionSample() {
        Assumptions.assumingThat("1".equals("1"), () -> assertEquals("1","1"));

    }

}
