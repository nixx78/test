package lv.nixx.poc.cucumber.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features/transaction/monthly", 
		plugin = { "pretty", "html:target/cucumber/transaction/monthly.html" },
		glue = "lv.nixx.poc.cucumber.stepdef")
public class TransactionMonthlyFeatureRunner {
}