# Galaxy (spy)wear App
This repo contains patches to the galaxy wear app and galaxy-buds-live plugin which remove the permission requirements.

The app usually requires almost all permissions available in Android, and refuses to work when they are not granted. I just want to update my firmware and control what the buttons of my earbuds do. I do not want my buds to read my notifications, sms, calendar and contacts, thank you very much.

Aside from the permissions check removals, no other changes are made.

The galaxy-wear ecosystem is based on plugins. There is the main galaxy wear app, which communicates with plugin-apps for each specific device. Currently only the galaxy-buds-live plugin is supported, as they are the only device I have access to.

Both the galaxy wear app and the plugins need to be signed with the same key, so interworking with google play store releases is not possible.

Up to date as of 2021/01/03. Tested only on GrapheneOS, YMMV.

Sometimes after installing both the wear app and the plugin, the app crashes after pairing before opening the plugin interface. Should this happen, just reinstall the plugin. Should only be necessary once, no idea what causes this.

## Building
You can use either Android Studio, or gradle directly. There is a "global" gradle build file, which invokes the same command in both sub-apps. You can build and install release/debug builds.

- Clean all build artifacts: `./gradlew clean`
- Build Debug Apk: `./gradlew build`
  - output apk in: `*-patch/app/build/outputs/apk/debug/app-debug.apk`
- Build Release Apk: `./gradlew release`
  - output apk in: `*-patch/app/build/outputs/apk/release/app-release.apk`
- Install Debug Apk: `./gradlew install`
- Install Release Apk: `./gradlew installRelease`

Release builds require a keystore to be present and referenced by `~/.gradle/gradle.properties`, which should contain

```
RELEASE_STORE_FILE={path to your keystore}
RELEASE_STORE_PASSWORD=*****
RELEASE_KEY_ALIAS=*****
RELEASE_KEY_PASSWORD=*****
```

## How the patches are done
The app is patched with [dexpatcher](https://github.com/DexPatcher/dexpatcher-tool). The code is based on the [dexpatcher-gradle-samples](https://github.com/DexPatcher/dexpatcher-gradle-samples) patched-app example in version 2.0.0 (the latest working version?). See commit [b00c117](https://github.com/tlambertz/galaxy-spy-wear-app/commit/b00c1175116d3eea37407ecc4a21d09ee932c805) for my initial changes required to adapt the sample to the plugin/wear app.

They are based on the source-code structure as output by the jadx decompiler. This allows easy copying of imports and function signatures.

## Scratchpad
Both apps need to be present, since the plugin has no registered launch activity.

The wear app determines the bluetooth id, and starts the plugin via an intent. We can send this intent ourselves with a command like `adb shell am start -n 'com.samsung.accessory.neobeanmgr/com.samsung.accessory.hearablemgr.module.LaunchActivity' -e "deviceid" "AA:BB:CC:00:13:37"`. (the device id is shown in the bluetooth connection tab in your android settings. Hex letters have to be capitalized! If everything works correctly, the app prompts a pairing window when you press continue on the opened app. Otherwise nothing happens.)

This approach has one problem: there is no way to dynamically/graphically choose the deviceid in the plugin, this done in the wear app. We could hardcode a device id though.