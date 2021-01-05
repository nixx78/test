package lv.nixx.poc.junit5.benchmark;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BenchmarkExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
	
	private long launchTime;
	private Map<String, String> report = new HashMap<>();

	@Override
	public void beforeTestExecution(ExtensionContext context) {
		launchTime = System.currentTimeMillis();
	}

	@Override
	public void afterTestExecution(ExtensionContext context) {
		final Optional<Class<?>> testClass = context.getTestClass();
		final Optional<Method> testMethod = context.getTestMethod();
		
		String className = testClass.map(Class::getSimpleName).orElse("");
		String method = testMethod.isPresent() ? testMethod.get().getName() : "";
		
		long elapsedTime = System.currentTimeMillis() - launchTime;
		
		String message = className + ":" + method + "Test took :" + elapsedTime;
		report.put("Benchmark", message);
		
		context.publishReportEntry(report);
	}

	
}