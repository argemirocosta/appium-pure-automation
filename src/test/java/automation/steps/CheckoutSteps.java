package automation.steps;

import automation.pages.CartPage;
import automation.pages.CheckoutPage;
import automation.pages.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckoutSteps {

    private final CartPage cart = new CartPage();
    private final LoginPage login = new LoginPage();
    private final CheckoutPage checkout = new CheckoutPage();

    @When("the user checks out with valid shipping and payment details")
    public void theUserChecksOutWithValidShippingAndPaymentDetails() {
        cart.proceedToCheckout();
        // checkout asks for login when the user is not logged in yet
        login.logInWithValidCredentials();
        checkout.enterShippingAddress();
        checkout.enterPaymentDetails();
        checkout.placeOrder();
    }

    @Then("the order is confirmed")
    public void theOrderIsConfirmed() {
        assertEquals("Checkout Complete", checkout.confirmationTitle());
    }
}
