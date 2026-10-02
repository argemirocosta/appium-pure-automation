package automation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class Hooks {

    public static AppiumDriver driver;

    public static boolean isIos() {
        return System.getProperty("platform", "android").equalsIgnoreCase("ios");
    }

    @Before
    public void openApp() throws Exception {
        URL server = URI.create("http://127.0.0.1:4723").toURL();
        String apps = System.getProperty("user.dir") + "/apps/";

        if (isIos()) {
            driver = new IOSDriver(server, new XCUITestOptions()
                    .setDeviceName("iPhone 17")
                    .setPlatformVersion("26.5")
                    .setApp(apps + "My Demo App.app"));
        } else {
            driver = new AndroidDriver(server, new UiAutomator2Options()
                    .setAvd("Pixel_9a")
                    .setApp(apps + "mda-2.3.0-27.apk")
                    // the splash screen can be gone before Appium sees it
                    .setAppWaitActivity("*"));
            dismissCompatibilityDialog();
        }
    }

    // Emulator images with 16 KB pages show an "Android App Compatibility"
    // system dialog over the app on launch; tap OK when it appears.
    private void dismissCompatibilityDialog() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("android:id/button2")))
                    .click();
        } catch (TimeoutException e) {
            // no dialog on this device
        }
    }

    @After
    public void closeApp() {
        if (driver != null) {
            driver.quit();
        }
    }
}
