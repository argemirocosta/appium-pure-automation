package automation.pages;

import org.openqa.selenium.By;

public class CartPage extends Page {

    private static final By ANDROID_CART = androidId("cartRL");
    private static final By IOS_CART = accessibilityId("Cart-tab-item");
    private static final By ANDROID_ITEM_COUNT = androidId("itemsTV");
    // the digits keep the hidden "No Items" label of the empty cart out of the match
    private static final By IOS_ITEM_COUNT =
            iosPredicate("type == 'XCUIElementTypeStaticText' AND name MATCHES '[0-9]+ Items'");
    private static final By ANDROID_PROCEED_TO_CHECKOUT = androidId("cartBt");
    private static final By IOS_PROCEED_TO_CHECKOUT = accessibilityId("ProceedToCheckout");

    public void open() {
        tap(ANDROID_CART, IOS_CART);
    }

    /** The total line as shown by the app, for example "1 Items". */
    public String itemCountText() {
        return find(ANDROID_ITEM_COUNT, IOS_ITEM_COUNT).getText();
    }

    public void proceedToCheckout() {
        tap(ANDROID_PROCEED_TO_CHECKOUT, IOS_PROCEED_TO_CHECKOUT);
    }
}
