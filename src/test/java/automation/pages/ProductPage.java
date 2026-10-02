package automation.pages;

import org.openqa.selenium.By;

public class ProductPage extends Page {

    private static final By ANDROID_ADD_TO_CART = androidId("cartBt");
    private static final By IOS_ADD_TO_CART = accessibilityId("AddToCart");

    public void addToCart() {
        tap(ANDROID_ADD_TO_CART, IOS_ADD_TO_CART);
    }
}
