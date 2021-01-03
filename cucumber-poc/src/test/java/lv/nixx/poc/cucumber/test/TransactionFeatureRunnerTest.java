package lv.nixx.poc.cucumber.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features/transaction", 
		plugin = { "pretty", "html:target/cucumber/transaction/plain" }, 
		glue = "lv.nixx.poc.cucumber.stepdef")
public class TransactionFeatureRunnerTest {
}