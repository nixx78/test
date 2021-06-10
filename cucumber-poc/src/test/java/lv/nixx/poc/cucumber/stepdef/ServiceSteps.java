package lv.nixx.poc.cucumber.stepdef;

import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lv.nixx.poc.cucumber.service.Service;

import java.util.List;
import java.util.Map;

public class ServiceSteps {

    Service service;

    @Given("^Service is available$")
    public void service_is_available() {
        service = new Service();
    }

    @Given("Users with passwords exists:")
    public void users_with_passwords_exists(List<Map<String, String>> users) {
        //TODO Implement this method
    }

    @When("User {string} is login successfully")
    public void userIsLoggedIn(String user) {
        System.out.println("User login: " + user);
    }

    @Then("^Calculate: (\\d+) \\+ (\\d+) expected (\\d+)$")
    public void calculate(int a, int b, int r) {
        assertEquals(r, service.calculate(a, b));
    }

}
