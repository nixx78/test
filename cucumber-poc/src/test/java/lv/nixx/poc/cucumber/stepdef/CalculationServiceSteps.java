package lv.nixx.poc.cucumber.stepdef;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lv.nixx.poc.cucumber.service.CalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CalculationServiceSteps {

    private static final Logger log = LoggerFactory.getLogger(CalculationServiceSteps.class);

    CalculationService calculationService;

    @Given("Service is available")
    public void service_is_available() {
        calculationService = new CalculationService();
    }

    @Given("Users with passwords exists:")
    public void users_with_passwords_exists(List<Map<String, String>> users) {
        //TODO Implement this method
    }

    @When("User {string} is login successfully")
    public void userIsLoggedIn(String user) {
        log.info("User login: " + user);
    }

    @Then("Calculate: {int} + {int} expected {int}")
    public void calculate(int a, int b, int r) {
        assertEquals("Calculate wrong result", r, calculationService.calculate(a, b));
    }

}
