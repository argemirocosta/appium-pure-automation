package automation.pages;

import org.openqa.selenium.By;

public class CheckoutPage extends Page {

    private static final By ANDROID_FULL_NAME = androidId("fullNameET");
    private static final By IOS_FULL_NAME = iosField("Rebecca Winter");
    private static final By ANDROID_ADDRESS_LINE_1 = androidId("address1ET");
    private static final By IOS_ADDRESS_LINE_1 = iosField("Mandorley 112");
    private static final By ANDROID_CITY = androidId("cityET");
    private static final By IOS_CITY = iosField("Truro");
    private static final By ANDROID_ZIP_CODE = androidId("zipET");
    private static final By IOS_ZIP_CODE = iosField("89750");
    private static final By ANDROID_COUNTRY = androidId("countryET");
    private static final By IOS_COUNTRY = iosField("United Kingdom");

    private static final By ANDROID_CARD_HOLDER = androidId("nameET");
    private static final By IOS_CARD_HOLDER = iosField("Maxim Winter");
    private static final By ANDROID_CARD_NUMBER = androidId("cardNumberET");
    private static final By IOS_CARD_NUMBER = iosField("3258 1265 7568 7896");
    private static final By ANDROID_EXPIRATION_DATE = androidId("expirationDateET");
    private static final By IOS_EXPIRATION_DATE = iosField("03/25");
    private static final By ANDROID_SECURITY_CODE = androidId("securityCodeET");
    private static final By IOS_SECURITY_CODE = iosField("123");

    // On Android "To Payment", "Review Order" and "Place Order" share the same id.
    private static final By ANDROID_NEXT_BUTTON = androidId("paymentBtn");
    private static final By IOS_TO_PAYMENT = accessibilityId("To Payment");
    private static final By IOS_REVIEW_ORDER = accessibilityId("Review Order");
    private static final By IOS_PLACE_ORDER = accessibilityId("Place Order");

    private static final By ANDROID_COMPLETE_TITLE = androidId("completeTV");
    private static final By IOS_COMPLETE_TITLE = accessibilityId("Checkout Complete");

    // The iOS text fields have no identifier, only their placeholder text.
    private static By iosField(String placeholder) {
        return iosPredicate("type == 'XCUIElementTypeTextField' AND placeholderValue == '" + placeholder + "'");
    }

    public void enterShippingAddress() {
        type(ANDROID_FULL_NAME, IOS_FULL_NAME, "Rebecca Winter");
        type(ANDROID_ADDRESS_LINE_1, IOS_ADDRESS_LINE_1, "Mandorley 112");
        type(ANDROID_CITY, IOS_CITY, "Truro");
        type(ANDROID_ZIP_CODE, IOS_ZIP_CODE, "89750");
        type(ANDROID_COUNTRY, IOS_COUNTRY, "United Kingdom");
        tap(ANDROID_NEXT_BUTTON, IOS_TO_PAYMENT);
    }

    public void enterPaymentDetails() {
        type(ANDROID_CARD_HOLDER, IOS_CARD_HOLDER, "Rebecca Winter");
        type(ANDROID_CARD_NUMBER, IOS_CARD_NUMBER, "3258125675687891");
        type(ANDROID_EXPIRATION_DATE, IOS_EXPIRATION_DATE, "0330");
        type(ANDROID_SECURITY_CODE, IOS_SECURITY_CODE, "123");
        tap(ANDROID_NEXT_BUTTON, IOS_REVIEW_ORDER);
    }

    public void placeOrder() {
        tap(ANDROID_NEXT_BUTTON, IOS_PLACE_ORDER);
    }

    public String confirmationTitle() {
        return find(ANDROID_COMPLETE_TITLE, IOS_COMPLETE_TITLE).getText();
    }
}
