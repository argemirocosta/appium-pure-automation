package automation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                    // an iPad because its keyboard can be dismissed; the iPhone
                    // keyboard stays over the buttons at the bottom of the forms
                    .setDeviceName("iPad (A16)")
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
    // system dialog over the app. "Don't Show Again" keeps it away for the
    // rest of the session; OK would bring it back on every new screen.
    private void dismissCompatibilityDialog() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("android:id/button1")))
                    .click();
        } catch (TimeoutException e) {
            // no dialog on this device
        }
    }

    @After
    public void closeApp(Scenario scenario) throws Exception {
        if (driver == null) {
            return;
        }
        try {
            if (scenario.isFailed()) {
                saveScreenshot(scenario);
            }
        } finally {
            driver.quit();
            driver = null;
        }
    }

    // Saves the screen at the moment of the failure to screenshots/ and
    // attaches it to the scenario, so reports can show it.
    private void saveScreenshot(Scenario scenario) throws Exception {
        byte[] png = driver.getScreenshotAs(OutputType.BYTES);
        String name = System.getProperty("platform", "android") + "-"
                + scenario.getName().replaceAll("[^A-Za-z0-9]+", "-") + "-"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        Path file = Path.of("screenshots", name + ".png");
        Files.createDirectories(file.getParent());
        Files.write(file, png);
        scenario.attach(png, "image/png", name);
    }
}
