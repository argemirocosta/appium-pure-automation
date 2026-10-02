# Environment setup (macOS)

How to prepare a Mac to run this project's mobile tests with Java, Maven,
JUnit, Cucumber and Appium, on an Android emulator and an iOS simulator.

App under test: **Sauce Labs My Demo App** (Android and iOS).

No physical device, Apple Developer account or Sauce Labs account is needed.

## What you will install

| # | Tool | Used for | Where it comes from |
|---|---|---|---|
| 1 | Homebrew | Installing the other tools | https://brew.sh |
| 2 | JDK 21 | Compiling and running the tests | Homebrew (`temurin@21`) |
| 3 | Maven | Build and dependency management | Homebrew |
| 4 | Node.js | Runtime for the Appium server | Homebrew |
| 5 | Xcode | iOS simulator and build tools | [App Store](https://apps.apple.com/app/xcode/id497799835) |
| 6 | iOS runtime | System image the simulator runs | Inside Xcode |
| 7 | Android Studio | Android SDK and emulator | https://developer.android.com/studio |
| 8 | Appium | Automation server | npm |
| 9 | Appium drivers | `uiautomator2` (Android), `xcuitest` (iOS) | Appium CLI |
| 10 | Appium Inspector | Inspecting screens to find locators | [GitHub releases](https://github.com/appium/appium-inspector/releases) (not Homebrew) |
| 11 | My Demo App | App under test | GitHub releases (see step 6) |

Steps 2 and 3 involve large downloads; start them in parallel.

## 1. Base tools: Homebrew, JDK, Maven, Node.js

1. Install Homebrew, if you do not have it:

   ```sh
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

2. Install the JDK, Maven and Node.js:

   ```sh
   brew install --cask temurin@21
   brew install maven node
   ```

3. Add `JAVA_HOME` to `~/.zshrc`:

   ```sh
   export JAVA_HOME="$(/usr/libexec/java_home -v 21)"
   ```

4. Reload the shell and check:

   ```sh
   source ~/.zshrc
   java -version     # 21.x
   mvn -v            # must report Java 21
   node -v
   npm -v
   ```

## 2. Xcode and the iOS simulator

The simulator is not a separate download: it ships with Xcode, and the iOS
runtime is downloaded from inside Xcode.

1. Install **Xcode** from the
   [App Store](https://apps.apple.com/app/xcode/id497799835).
   Alternative: download the `.xip` from
   https://developer.apple.com/download/all/ (free Apple ID login) and move
   `Xcode.app` to `/Applications`.

2. Point the command line tools to the full Xcode and accept the license:

   ```sh
   sudo xcode-select -s /Applications/Xcode.app/Contents/Developer
   sudo xcodebuild -license accept
   xcodebuild -runFirstLaunch
   ```

   Without this, `xcodebuild` and `simctl` keep failing with
   `tool 'xcodebuild' requires Xcode`.

3. Download the iOS runtime. Either in Xcode, under
   **Settings > Components > iOS**, or from the terminal:

   ```sh
   xcodebuild -downloadPlatform iOS
   ```

4. Check:

   ```sh
   xcodebuild -version
   xcrun simctl list devices available
   ```

   The second command must list iPhones. Note one device name (for example
   `iPhone 17`) and its iOS version; they are used in the test capabilities.

5. Open the simulator once to confirm it boots:

   ```sh
   open -a Simulator
   ```

## 3. Android Studio, SDK and emulator

1. Install Android Studio, either from https://developer.android.com/studio
   (choose the **Mac with Apple chip** `.dmg` on Apple Silicon) or with:

   ```sh
   brew install --cask android-studio
   ```

2. Open Android Studio and finish the setup wizard with the **Standard**
   installation. It downloads the SDK to `~/Library/Android/sdk`.

3. In **Settings > Languages & Frameworks > Android SDK > SDK Tools**, select
   and install:
   - Android SDK Platform-Tools
   - Android SDK Command-line Tools (latest)
   - Android Emulator

4. Add the environment variables to `~/.zshrc`:

   ```sh
   export ANDROID_HOME="$HOME/Library/Android/sdk"
   export PATH="$PATH:$ANDROID_HOME/platform-tools"
   export PATH="$PATH:$ANDROID_HOME/emulator"
   export PATH="$PATH:$ANDROID_HOME/cmdline-tools/latest/bin"
   ```

   Then reload the shell:

   ```sh
   source ~/.zshrc
   ```

5. Create an emulator in **Device Manager > Create Virtual Device**:
   - Device: a recent Pixel
   - System image: API 34 or 35 with Google APIs; **arm64-v8a** on Apple
     Silicon, x86_64 on Intel Macs
   - Name: something simple without spaces, for example `Pixel_API_35`

6. Check:

   ```sh
   adb version
   emulator -list-avds
   emulator -avd Pixel_API_35     # boots the emulator
   adb devices                    # in another terminal; must list emulator-5554
   ```

## 4. Appium and drivers

1. Install the Appium server:

   ```sh
   npm install -g appium
   appium -v
   ```

2. Install the drivers for both platforms:

   ```sh
   appium driver install uiautomator2
   appium driver install xcuitest
   appium driver list --installed
   ```

3. Run each driver's diagnostics (only after steps 2 and 3):

   ```sh
   appium driver doctor uiautomator2
   appium driver doctor xcuitest
   ```

   All required checks must pass. Warnings about optional items (for example
   `ffmpeg`, `bundletool`, `idb`) can be ignored for now.

4. Start the server to confirm it works:

   ```sh
   appium
   ```

   It must listen on `http://127.0.0.1:4723`. Stop it with `Ctrl+C`.

If Appium or a driver rejects your Node.js version during installation, switch
to a Node.js LTS release.

## 5. Appium Inspector

GUI tool to inspect the app's screens and find element locators.

The Homebrew cask (`appium-inspector`) was disabled on 2026-09-01 because the
app is not notarized and fails the macOS Gatekeeper check, so install it from
the GitHub release instead.

1. Download the `.dmg` for your Mac from
   https://github.com/appium/appium-inspector/releases:
   - Apple Silicon: `Appium-Inspector-<version>-mac-arm64.dmg`
   - Intel: `Appium-Inspector-<version>-mac-x64.dmg`

2. Open the `.dmg` and drag **Appium Inspector** to `/Applications`.

3. Remove the quarantine flag, otherwise macOS refuses to open the app:

   ```sh
   xattr -cr "/Applications/Appium Inspector.app"
   ```

4. Open the app from `/Applications`.

## 6. Download the app under test

The app binaries go in the `apps/` folder at the project root.

| Platform | Release | File to download |
|---|---|---|
| Android | [2.3.0](https://github.com/saucelabs/my-demo-app-android/releases/tag/2.3.0) | `mda-2.3.0-27.apk` |
| iOS | [2.3.0](https://github.com/saucelabs/my-demo-app-ios/releases/tag/2.3.0) | `SauceLabs-Demo-App.Simulator.zip` |

From the project root:

```sh
mkdir -p apps
curl -L -o apps/mda-2.3.0-27.apk \
  https://github.com/saucelabs/my-demo-app-android/releases/download/2.3.0/mda-2.3.0-27.apk
curl -L -o apps/SauceLabs-Demo-App.Simulator.zip \
  https://github.com/saucelabs/my-demo-app-ios/releases/download/2.3.0/SauceLabs-Demo-App.Simulator.zip
unzip apps/SauceLabs-Demo-App.Simulator.zip -d apps/
```

The iOS `.zip` unpacks into the folder `My Demo App.app`; that folder is what
the simulator installs.

### Why not the `.ipa`?

The Android emulator runs real Android, so the same `.apk` works on emulators
and physical devices. The iOS simulator does not emulate iPhone hardware: it
runs the app as a macOS process built against a different SDK
(`iphonesimulator` instead of `iphoneos`). An `.ipa` is built and signed for
physical devices and the simulator refuses to install it, even on Apple
Silicon.

### Files in the releases you do not need

- `mda-androidTest-*.apk`: the app's own Espresso tests.
- `SauceLabs-Demo-App.ipa` and the other `.ipa` files: physical iPhones only.
- `SauceLabs-Demo-App.Simulator.XCUITest.zip`, `SauceLabs-Demo-App-Runner.*`
  and the `.xctestrun` file: native XCUITest tests. The name is close to the
  right one; the file you want is `Simulator.zip` without `XCUITest`.

## 7. Final validation

With the Android emulator and the iOS simulator both running:

1. Install the app manually on each one:

   ```sh
   adb install apps/mda-2.3.0-27.apk
   xcrun simctl install booted "apps/My Demo App.app"
   ```

2. Open the app from its icon on each device and confirm the product catalog
   screen appears.

3. Checklist:

   - [ ] `java -version` and `mvn -v` report Java 21
   - [ ] `xcodebuild -version` shows the Xcode version
   - [ ] `xcrun simctl list devices available` lists iPhones
   - [ ] `echo $ANDROID_HOME` prints the SDK path
   - [ ] `adb devices` lists the emulator
   - [ ] `appium driver list --installed` shows `uiautomator2` and `xcuitest`
   - [ ] `appium driver doctor` has no failed required checks for either driver
   - [ ] `appium` starts on port 4723
   - [ ] My Demo App opens on the Android emulator
   - [ ] My Demo App opens on the iOS simulator

With every item checked, the machine is ready to run the project.
