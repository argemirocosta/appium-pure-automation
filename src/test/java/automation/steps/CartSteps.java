package automation.steps;

import automation.pages.CartPage;
import automation.pages.CatalogPage;
import automation.pages.ProductPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartSteps {

    private final CatalogPage catalog = new CatalogPage();
    private final ProductPage product = new ProductPage();
    private final CartPage cart = new CartPage();

    @When("the user adds the first product to the cart")
    public void theUserAddsTheFirstProductToTheCart() {
        catalog.openFirstProduct();
        product.addToCart();
        cart.open();
    }

    @Then("the cart contains {int} item(s)")
    public void theCartContainsItems(int count) {
        assertEquals(count + " Items", cart.itemCountText());
    }
}
