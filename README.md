# ShakeIt
![Screen 1](/screenshots/banner.png "Screen 1")

A multiplatform client application for [CocktailsAPI](https://cocktails.solvro.pl/)

# Build
Add this line to the project's **local.properties** file:
```
base_url=https://cocktails.solvro.pl/api/v1
```

# Currently supported platforms
- :iphone: Android<sup>1</sup>
- :computer: Desktop
- :green_apple: iOS<sup>2</sup>

> [!IMPORTANT]
> ShakeIt is not yet tested in macOS or iOS environment because I don't have any Apple machines.
> Planning to be fixed soon.

# Technology stack
* Kotlin Multiplatform
* Compose Multiplatform (+ adaptive layouts)
* Coroutines
* Koin
* Ktor client
* Paging 3
* Room
* DataStore

# About Kotlin Multiplatform project.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.
