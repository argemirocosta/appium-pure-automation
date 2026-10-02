package automation.pages;

import automation.Hooks;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Base for page objects: each element is given as an Android locator and an iOS locator. */
public abstract class Page {

    private static final String ANDROID_ID_PREFIX = "com.saucelabs.mydemoapp.android:id/";

    protected static By androidId(String id) {
        return AppiumBy.id(ANDROID_ID_PREFIX + id);
    }

    protected static By accessibilityId(String id) {
        return AppiumBy.accessibilityId(id);
    }

    protected static By iosPredicate(String predicate) {
        return AppiumBy.iOSNsPredicateString(predicate);
    }

    protected WebElement find(By android, By ios) {
        By locator = Hooks.isIos() ? ios : android;
        return new WebDriverWait(Hooks.driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void tap(By android, By ios) {
        find(android, ios).click();
    }

    protected void type(By android, By ios, String text) {
        find(android, ios).sendKeys(text);
        if (Hooks.isIos()) {
            // the keyboard would cover the buttons at the bottom of the screen
            ((IOSDriver) Hooks.driver).hideKeyboard();
        }
    }
}
