package lv.nixx.poc.junit5.service;

public class Calculator {

	public Integer add(Integer a, Integer b) {

		if (a == null || b == null) {
			throw new IllegalArgumentException("Argument can't be null");
		}

		return a + b;
	}

	public Integer subtract(Integer a, Integer b) {
		return a - b;
	}
}