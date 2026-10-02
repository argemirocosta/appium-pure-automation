# Running the emulator and the simulator from the terminal

How to start the Android emulator and the iOS simulator without opening
Android Studio or Xcode.

Both tools must stay installed, because the emulator and the simulator ship
with them, but neither needs to be open. See
[environment-setup.md](environment-setup.md) for the installation.

## Android emulator

1. List the emulators (AVDs) that exist:

   ```sh
   emulator -list-avds
   ```

2. Start one by name:

   ```sh
   emulator -avd Pixel_9a
   ```

   The command holds the terminal while the emulator is open. To get the
   terminal back, run it in the background:

   ```sh
   emulator -avd Pixel_9a &
   ```

3. Check that it is connected:

   ```sh
   adb devices     # must list emulator-5554
   ```

4. Shut it down:

   ```sh
   adb emu kill
   ```

The `emulator` and `adb` commands come from the Android SDK and are on the
`PATH` through the variables added to `~/.zshrc` during setup.

## iOS simulator

1. List the simulators:

   ```sh
   xcrun simctl list devices available
   ```

2. Boot one by name:

   ```sh
   xcrun simctl boot "iPhone 17"
   ```

   This starts the device in the background, with no window.

3. Open the Simulator window to see it:

   ```sh
   open -a Simulator
   ```

   Simulator is a separate app from Xcode, so Xcode stays closed.

4. Shut it down:

   ```sh
   xcrun simctl shutdown "iPhone 17"
   ```

## Running the tests

Starting the devices by hand is optional. Appium starts them when a session
begins:

- **Android:** the `avd` capability (`setAvd("Pixel_9a")` in `Hooks.java`)
  makes Appium launch the emulator if it is not running.
- **iOS:** Appium boots the simulator named in the capabilities if it is shut
  down. The first session takes longer, because Appium builds and installs
  WebDriverAgent on the simulator.

Only the Appium server has to be started manually:

```sh
appium                           # in one terminal
mvn test -Dplatform=android      # in another
mvn test -Dplatform=ios
```

Start the devices beforehand when you want a faster first run or when you are
going to use Appium Inspector.
