package automation.steps;

import automation.pages.LoginPage;
import io.cucumber.java.en.When;

public class LoginSteps {

    private final LoginPage login = new LoginPage();

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() {
        login.open();
        login.logInWithValidCredentials();
    }
}
