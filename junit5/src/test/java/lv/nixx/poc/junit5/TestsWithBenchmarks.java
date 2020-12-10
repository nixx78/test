package lv.nixx.poc.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
// @Benchmark - possible to set there, for all methods
public class TestsWithBenchmarks {
	
	@Test
	@ExtendWith(BenchmarkExtension.class) 
	// One possibility, how to set Extension
	public void longTest() throws InterruptedException {
		Thread.sleep(100);
	}
	
	@Test
	@Benchmark 
	// Or another possibility to set Extension
	public void anotherLongTest() throws InterruptedException {
		Thread.sleep(200);
	}

}
