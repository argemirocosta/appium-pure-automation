package automation.pages;

import automation.Hooks;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginPage extends Page {

    // The demo app ships a different valid user on each platform.
    private static final String ANDROID_USERNAME = "bod@example.com";
    private static final String IOS_USERNAME = "bob@example.com";
    private static final String PASSWORD = "10203040";

    private static final By ANDROID_MENU = androidId("menuIV");
    private static final By IOS_MENU = accessibilityId("More-tab-item");
    private static final By ANDROID_LOGIN_MENU_ITEM = accessibilityId("Login Menu Item");
    private static final By IOS_LOGIN_MENU_ITEM = accessibilityId("LogOut-menu-item");

    private static final By ANDROID_USERNAME_FIELD = androidId("nameET");
    private static final By IOS_USERNAME_FIELD = AppiumBy.className("XCUIElementTypeTextField");
    private static final By ANDROID_PASSWORD_FIELD = androidId("passwordET");
    private static final By IOS_PASSWORD_FIELD = AppiumBy.className("XCUIElementTypeSecureTextField");
    private static final By ANDROID_LOGIN_BUTTON = androidId("loginBtn");
    private static final By IOS_LOGIN_BUTTON =
            iosPredicate("type == 'XCUIElementTypeButton' AND name == 'Login'");
    private static final By IOS_SAVE_PASSWORD_NOT_NOW = accessibilityId("Not Now");

    public void open() {
        tap(ANDROID_MENU, IOS_MENU);
        tap(ANDROID_LOGIN_MENU_ITEM, IOS_LOGIN_MENU_ITEM);
    }

    public void logInWithValidCredentials() {
        type(ANDROID_USERNAME_FIELD, IOS_USERNAME_FIELD, Hooks.isIos() ? IOS_USERNAME : ANDROID_USERNAME);
        type(ANDROID_PASSWORD_FIELD, IOS_PASSWORD_FIELD, PASSWORD);
        tap(ANDROID_LOGIN_BUTTON, IOS_LOGIN_BUTTON);
        if (Hooks.isIos()) {
            // iOS offers to save the typed password after every login
            dismissSavePasswordAlert();
        }
    }

    private void dismissSavePasswordAlert() {
        find(null, IOS_SAVE_PASSWORD_NOT_NOW);
        // a tap made while the alert is still animating in is ignored, so tap until it is gone
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(15))
                .ignoring(WebDriverException.class)
                .until(driver -> {
                    List<WebElement> buttons = driver.findElements(IOS_SAVE_PASSWORD_NOT_NOW);
                    if (buttons.isEmpty()) {
                        return true;
                    }
                    buttons.get(0).click();
                    return false;
                });
    }
}
