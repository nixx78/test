package lv.nixx.poc.junit5;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class DynamicTestingTest {
	
	@TestFactory
	public List<DynamicTest> createDynamicTestsReturnAsCollection() {
		
		return Arrays.asList(
				dynamicTest("A dynamic test", () -> assertTrue(true)),
				dynamicTest("Another dynamic test", () -> assertEquals(6, 3 * 2)
						));
	}

}
