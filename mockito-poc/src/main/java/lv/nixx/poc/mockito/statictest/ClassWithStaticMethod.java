package lv.nixx.poc.mockito.statictest;

public class ClassWithStaticMethod {
	
	public static String map(String v) {
		return StaticFormatter.format(getValue(v));
	}

	protected static String getValue(String v) {
		return v;
	}
	
}