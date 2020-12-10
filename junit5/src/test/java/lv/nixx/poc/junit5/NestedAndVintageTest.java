package lv.nixx.poc.junit5;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class NestedAndVintageTest {
	
	private Queue<String> items;
	
	@BeforeAll
	static void  executeBeforeAllTests() {
		System.out.println("beforeAllTests");
	}
	
	@AfterAll
	static void executeAfterAllTest() {
		System.out.println("afterAllTests");
	}

	@BeforeEach
	void setup() {
		items = new LinkedList<>();
	}

	@Test
	public void isQueueIsEmpty() {
		assertTrue(items.isEmpty());
	}

	@Nested
	class OperationsTest {
		@Test
		void addingOneElementShouldIncreaseSize() {
			items.add("Item");
			assertEquals(items.size(), 1);
		}
	}
	
	@org.junit.Test
	public void vintageTest() {
		assertNull(items);
	}
	
}