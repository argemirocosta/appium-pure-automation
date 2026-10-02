package automation.steps;

import automation.pages.CatalogPage;
import io.cucumber.java.en.Then;

public class CatalogSteps {

    private final CatalogPage catalog = new CatalogPage();

    @Then("the product catalog is displayed")
    public void theProductCatalogIsDisplayed() {
        catalog.waitUntilDisplayed();
    }
}
