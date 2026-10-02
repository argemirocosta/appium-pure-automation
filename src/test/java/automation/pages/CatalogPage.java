package automation.pages;

import org.openqa.selenium.By;

public class CatalogPage extends Page {

    private static final By ANDROID_PRODUCT_NAME = accessibilityId("Product Title");
    private static final By IOS_PRODUCT_NAME = accessibilityId("Product Name");
    private static final By PRODUCT_IMAGE = accessibilityId("Product Image");

    public void waitUntilDisplayed() {
        find(ANDROID_PRODUCT_NAME, IOS_PRODUCT_NAME);
    }

    public void openFirstProduct() {
        tap(PRODUCT_IMAGE, PRODUCT_IMAGE);
    }
}
