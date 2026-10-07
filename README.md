# appium-pure-automation

Mobile test automation for the
[Sauce Labs My Demo App](https://github.com/saucelabs/my-demo-app-android) on
Android and iOS, written in Java with Appium, Cucumber and JUnit. The same
scenarios run on both platforms.

## Stack

| Tool | Version |
|---|---|
| Java | 21 |
| Maven | 3.9 |
| Appium server | 3.8 |
| Appium drivers | uiautomator2 (Android), xcuitest (iOS) |
| Appium java-client | 10.1 |
| Cucumber | 8.0 |
| JUnit Platform | 6.1 |

## Devices

| Platform | Device | App |
|---|---|---|
| Android | Emulator `Pixel_9a` | `apps/mda-2.3.0-27.apk` |
| iOS | Simulator `iPad (A16)`, iOS 26.5 | `apps/My Demo App.app` |

The iOS tests run on an iPad because the app gives no way to dismiss the
iPhone keyboard, which covers the buttons at the bottom of the screen.

The device names are set in `src/test/java/automation/Hooks.java`. Change them
there to use other devices.

## Prerequisites

A Mac with the JDK, Maven, Node.js, Xcode, Android Studio, Appium and its
drivers installed, and the devices above created. See:

- [docs/environment-setup.md](docs/environment-setup.md): installing everything.
- [docs/running-devices.md](docs/running-devices.md): starting the emulator
  and the simulator from the terminal.

The app binaries are already in `apps/`.

## Running the tests

1. Start the Appium server and leave it running:

   ```sh
   appium
   ```

2. In another terminal, run the suite on one platform:

   ```sh
   mvn test -Dplatform=android   # default when -Dplatform is omitted
   mvn test -Dplatform=ios
   ```

   Appium boots the emulator or simulator if it is not running yet.

To run a single scenario, filter by its name:

```sh
mvn test -Dplatform=ios -Dcucumber.filter.name="Log in"
```

## Scenarios

| Feature | Scenario |
|---|---|
| Product catalog | The catalog is shown when the app opens |
| Login | Log in with valid credentials |
| Cart | Add a product to the cart |
| Checkout | Complete a purchase |

Each scenario opens the app on a fresh session and closes it at the end.

## Project structure

```
apps/                         app binaries under test
docs/                         setup and device guides
src/test/java/automation/
  Hooks.java                  opens and closes the app for each scenario
  RunCucumberTest.java        JUnit entry point for Cucumber
  pages/                      page objects, one Android and one iOS locator per element
  steps/                      step definitions, calling the page objects
src/test/resources/features/  Cucumber scenarios
```

## Adding a test

1. Write the scenario in a `.feature` file under `src/test/resources/features/`.
2. Find the locators of each element on both platforms with Appium Inspector.
3. Add them to a page object in `pages/`, which extends `Page` and uses its
   `find`, `tap` and `type` methods.
4. Add step definitions in `steps/` that call the page objects.
5. Run the scenario on both platforms.
