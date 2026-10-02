package automation;

import io.appium.java_client.AppiumBy;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CatalogSteps {

    private static final By ANDROID_PRODUCT = AppiumBy.accessibilityId("Product Title");
    private static final By IOS_PRODUCT = AppiumBy.accessibilityId("Product Name");

    @Then("the product catalog is displayed")
    public void theProductCatalogIsDisplayed() {
        By product = Hooks.isIos() ? IOS_PRODUCT : ANDROID_PRODUCT;
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(product));
    }
}
