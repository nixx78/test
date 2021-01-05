package lv.nixx.poc.junit5;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@RunWith(JUnitPlatform.class)
class DynamicTestingTest {

    @TestFactory
    public List<DynamicTest> createDynamicTestsReturnAsCollection() {

        return List.of(
                dynamicTest("A dynamic test", () -> assertTrue(true)),
                dynamicTest("Another dynamic test", () -> assertEquals(6, 3 * 2)
                ));
    }

}
