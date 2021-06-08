package lv.nixx.poc.cucumber.stepdef;

import static org.junit.Assert.assertEquals;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lv.nixx.poc.cucumber.service.Service;

public class ServiceSteps {

	Service service;

	@Given("^Service is available$")
	public void service_is_available() {
		service = new Service();
	}

	@When("User (.*) is login successfully")
	public void userIsLoggedIn(String user) {
	}

	@Then("^Calculate: (\\d+) \\+ (\\d+) expected (\\d+)$")
	public void calculate(int a, int b, int r) {
		assertEquals(r, service.calculate(a, b));
	}
	
}
