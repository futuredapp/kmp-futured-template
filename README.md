# Futured KMP Template

Hey there 👋

This is our template from which we build Kotlin Multiplatform applications that target Android and
iOS platforms.
It is our opinionated way of building KMP apps and shines a light on how we structure our
architecture and what tools and libraries we use.

To give you a short overview of our stack, we use:

- Native UI on both platforms. Jetpack Compose on Android and SwiftUI on iOS. The rest of the
  application is shared in KMP.
- [Decompose](https://github.com/arkivanov/Decompose) for sharing presentation logic and navigation
  state.
- The presentation layer follows the MVI-like design pattern.
- [Koin](https://insert-koin.io/) for dependency injection.
- [SKIE](https://skie.touchlab.co/) for better Kotlin->Swift interop (exhaustive enums, sealed
  classes, Coroutines support).
- [moko-resources](https://github.com/icerockdev/moko-resources) for sharing string (and other types
  of) resources.
- [apollo-kotlin](https://github.com/apollographql/apollo-kotlin) network client for apps that call
  GraphQL APIs.
- [ktorfit](https://github.com/Foso/Ktorfit) network client for apps that call plain HTTP APIs.
- [Jetpack DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) as a simple
  preferences storage (we have JSON-based and primitive implementations).
- [iOS-templates](https://github.com/futuredapp/iOS-templates) as template which generates a new iOS
  scene using MVVM-C architecture.

The template is a sample app with several screens to let you kick off the project with everything
set up, incl. navigation and some API calls.

-------8<------- CUT HERE AFTER CLONING -------8<-------

# Project Name

![kmp](https://img.shields.io/badge/multiplatform-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![compose](https://img.shields.io/badge/jetpack_compose-2bab6b.svg?style=for-the-badge&logo=android&logoColor=white)
![swiftui](https://img.shields.io/badge/swiftui-%23000000.svg?style=for-the-badge&logo=swift&logoColor=white)

![kotlin-version](https://img.shields.io/badge/kotlin-2.0.0-%237F52FF.svg?style=flat-square&logo=kotlin&logoColor=white)
![android-minsdk](https://img.shields.io/badge/minsdk-29-2bab6b.svg?style=flat-square&logo=android&logoColor=white)
![android-targetsdk](https://img.shields.io/badge/targetsdk-34-2bab6b.svg?style=flat-square&logo=android&logoColor=white)
![ios-target](https://img.shields.io/badge/target-16.0-%23000000.svg?style=flat-square&logo=apple&logoColor=white)

~~Short project description.~~

## Project info

- Deadline: ~~**--. --. ----**~~
- Design: ~~Figma (add link)~~
- ~~Backend: GrahlQL / Apiary / OpenAPI (add link)~~
    - ~~Prod: https://live.app.com~~
    - ~~Dev: https://staging.app.com~~
- ~~Localizations: Czech, English – POEditor / Google Sheets (add link)~~
- [Architecture decision records](doc/adr/README.md)

### KMP

- Product Flavors: dev, prod
- Use-Cases: Kotlin Coroutines [cr-usecases](https://github.com/futuredapp/arkitekt)

### Android

- ApplicationId: ~~`app.futured.project`~~
- minSdk: ~~`29`~~
- targetSdk: ~~`36`~~
- Supports: ~~**Dark mode, landscape orientation**~~
- Build Variants: debug, enterprise, release

### iOS

- Deployment target: ~~**16.0**~~
- Bundle identifier: ~~`app.futured.project`~~
- Supports: ~~**Dark mode, landscape orientation, iPadOS, watchOS**~~
- Language: ~~**Swift 5.0**~~
- IDE: ~~**Xcode 11.0**~~
- Dependency management: [Swift package manager](https://swift.org/package-manager/)
- Command line tools: **[Fastlane](https://docs.fastlane.tools)**
- Code style:
    - **[SwiftLint](https://swift.org/package-manager/)**
    - **[Danger](https://github.com/futuredapp/danger)**

## Team:

- ~~Jana Nováková, PM, <jana.novakova@futured.app>~~
- ~~Jan Novák, iOS developer, <jan.novak@futured.app>~~
- ~~John Newman, tester, <john.newman@futured.app>~~

## Used Tools

- Code style - **[ktlint](https://ktlint.github.io/)**, *
  *[detekt](https://arturbosch.github.io/detekt/)**, *
  *[Android lint](http://tools.android.com/tips/lint)**, *
  *[Danger](https://github.com/futuredapp/danger)**
- Kotlin -> Swift interop - **[skie](https://skie.touchlab.co/)**

### ~~Test accounts~~

- ~~dev - login: `a@a.com`, password: `hesloheslo`~~

### Security Standard

This project complies with ~~Standard (F0), High (F1), Highest (F2)~~ security standard.

~~[Project specific standard](www.notion.so)~~

## Gradle tasks

1. `clean` - Remove all `build` folders
2. `lintCheck` - Run `ktlint`, `detekt` checks. The same runs on CI.
3. `ktlintFormat` - Reformat source code according to ktlint rules.
4. `assembleAndCopyDebugSwiftPackage` - Assemble debug KMP XCFramework and copy it into local iOS
   Swift Package as dependency. (This task shouldn't be used directly, build the KMP target in
   Xcode, instead.)
5. `assembleAndCopyReleaseSwiftPackage` - Assemble release KMP XCFramework and copy it into local
   iOS Swift Package as dependency. (This task shouldn't be used directly, build the KMP target in
   Xcode, instead.)
6. `generateMRcommonMain` - Regenerate shared resource IDs.
7. `:shared:network:graphql:downloadApolloSchemaFromIntrospection` - Download the latest Apollo
   schema.
8. `:shared:network:graphql:generateApolloSources` - Generate Apollo sources (rebuilds models after
   adding modifying queries, mutations, etc.).

## Kotlin Multiplatform Swift Package Integration

### Architecture Overview

The iOS application integrates with KMP code through a local Swift Package Manager (SPM) package
located at `iosApp/shared/KMP/`. This package wraps the XCFramework generated by the KMP Gradle
plugin, providing a clean and modern dependency management approach.

Key components:

1. **Swift Package**: Located at `iosApp/shared/KMP/` with `Package.swift` defining the binary
   target
2. **XCFramework**: Generated by Gradle and referenced by the Swift Package
3. **Makefile**: Automates the build process based on environment variables
4. **Build Configuration**: Environment variables defined in `.xcconfig` files

### Environment Variables

Four environment variables control the build process:

- **`KMP_FRAMEWORK_BUILD_TYPE`**: Specifies the framework build type (`debug` or `release`)
    - Set in `.xcconfig` files for each Xcode build configuration
    - Controls optimization level and debug information
    - Determines which Gradle task to run (`assembleAndCopyDebugSwiftPackage` or
      `assembleAndCopyReleaseSwiftPackage`)

- **`KMP_BUILD_FLAVOR`**: Specifies the product flavor (see [Product Flavors](#product-flavors))
    - Set in `.xcconfig` files for each Xcode build configuration
    - Controls API endpoints and environment-specific configuration
    - Passed to Gradle as `-P buildkonfig.flavor=$(KMP_BUILD_FLAVOR)`

- **`KMP_BUILD_MODE`**: Controls which architectures are compiled (`simulator`, `device`, or `all`)
    - `simulator` — builds only `iosSimulatorArm64` (fastest for local development)
    - `device` — builds only `iosArm64`
    - `all` — builds both targets (default, required for XCFramework distribution)
    - Configured in `.xcconfig` files; Beta and Release always use `all`
    - Passed to Gradle as `-PkmpBuildMode=$(KMP_BUILD_MODE)`
- 
- **`KMP_IS_STATIC`**: Controls whether the KMP XCFramework is linked statically or dynamically
    - `false` (dynamic) — set in `Debug.xcconfig`; **required for SwiftUI previews** to work in
      Xcode, as the preview process cannot load a statically linked KMP framework
    - `true` (static) — set in `Release.xcconfig` and `Beta.xcconfig`; used for beta (TestFlight)
      and release builds
    - If not set, Gradle defaults to static (`true`)
    - Passed to Gradle as `-PisStatic=$(KMP_IS_STATIC)`

### Local Build Configuration

Developers can override `KMP_BUILD_MODE` locally for faster iteration:

1. Copy `iosApp/Config Files/Local.default.xcconfig` → `iosApp/Config Files/Local.xcconfig`
2. Set `KMP_BUILD_MODE = simulator` in `Local.xcconfig`
3. `Local.xcconfig` is gitignored — changes won't affect other developers

The Debug configuration includes both files (`Local.default.xcconfig` with optional include for
`Local.xcconfig`), so local overrides take precedence. Beta and Release configurations hardcode
`KMP_BUILD_MODE = all` and do not use local overrides.

### Development Workflow

To build and update the KMP package during development:

1. **Select and build the "Build KMP" target in Xcode**:
    - This target's build phase runs the Makefile with the appropriate environment variables
    - The Makefile detects the build configuration and runs the corresponding Gradle task
    - The XCFramework is generated and copied to the Swift Package location

2. **Alternative manual build**:
    - `./gradlew assembleAndCopyDebugSwiftPackage`
    - or if you wish to specify product flavor (default is always `dev`):
      `./gradlew assembleAndCopyDebugSwiftPackage -P buildkonfig.flavor=[dev|prod]`

3. **After KMP code changes**:
    - Always rebuild the "KMP Package" target to update the XCFramework
    - Then build the main app target to use the updated KMP code

### SwiftUI Previews

SwiftUI previews require a **dynamically linked** KMP framework. The `Debug` build configuration
sets `KMP_IS_STATIC = false` in `Debug.xcconfig` to enable this. `Beta` and `Release`
configurations use static linking.

#### Preview mock pattern

Shared KMP code exposes preview mock objects (e.g. `FirstScreenPreviews`) that provide fake,
no-op implementations of screen interfaces with configurable view states. These are consumed on the
iOS side inside `#if DEBUG` / `#Preview` blocks.

**Kotlin side** (`shared/feature/src/commonMain/kotlin/.../firstScreen/FirstScreenPreviews.kt`):

```kotlin
object FirstScreenPreviews {
    fun viewState(...) = FirstViewState(...)
    fun screen(viewState: FirstViewState = viewState()): FirstScreen = object : FirstScreen { ... }
}
```

**Swift side** (`iosApp/iosApp/Views/Screen/First/FirstView.swift`):

```swift
#if DEBUG
#Preview("FirstView") {
    FirstView(FirstViewModel(FirstScreenPreviews.shared.screen()))
}
#endif
```

When adding a new screen, create a `*Previews` object in the screen's package following the same
pattern.

## Navigation Structure

The app utilizes [Decompose](https://arkivanov.github.io/Decompose/) to share presentation logic and
navigation state in KMP.  
The following meta-description provides an overview of Decompose navigation tree:

```kotlin
Navigation("Root") {
    Slot {
        Screen("Login")
        Navigation("SignedIn") {
            // Bottom navigation stack
            Stack {
                // Home tab
                Navigation("Home") {
                    Stack {
                        Screen("First")
                        Screen("Second") {
                            Slot {
                                Screen("Picker")
                            }
                        }
                        Screen("Third")
                    }
                }
                // Profile tab
                Navigation("Profile") {
                    Stack {
                        Screen("Profile")
                    }
                }
            }
        }
    }
}
```

## Project Setup

### Initial script

Use the `init_template.sh` script to set up the template.
The script is a Kotlin-based tool (`init_template.main.kts`) that renames directories and package names throughout the project to match your specified package name.
The shell wrapper automatically ensures Kotlin is installed via SDKMAN if needed.

#### Usage

```shell
./init_template.sh
```

#### Note on iOS

The Swift Package XCFramework has to be build manually for the first time using `./gradlew assembleAndCopyDebugSwiftPackage` in order for XCode to resolve Swift Package as being valid.

### Product Flavors

The project utilizes [BuildKonfig](https://github.com/yshrsmz/BuildKonfig) plugin for implementing
build flavors in the network module.
There are two product flavors: `dev` and `prod`, which select API url used in `:shared:network:rest`
and `:shared:network:graphql` modules.

In general, the build flavor can be specified as a Gradle build flag

```shell
./gradlew whateverTask -P buildkonfig.flavor=dev
```

Please, refer to `:shared:network:*` module Gradle configs for more info.

#### Android

During local development, the build flavor can be specified in `gradle.properties` file like so:

```properties
buildkonfig.flavor=dev
```

#### Charles Proxy

[Charles Proxy](https://www.charlesproxy.com/documentation/welcome/) is enabled for debug and
enterprise builds.

#### iOS

On iOS, we utilize
.xcconfig [Build Configuration](https://www.kodeco.com/21441177-building-your-app-using-build-configurations-and-xcconfig)
files,
where each file per build configuration specifies a `KMP_BUILD_FLAVOR` environment variable.

This variable is then used in the "Build KMP" target to pass the flavor as Gradle build
flag.

```shell
./gradlew assembleAndCopyDebugSwiftPackage -P buildkonfig.flavor=$(KMP_BUILD_FLAVOR)
```
See [Makefile](iosApp/shared/KMP/Makefile) for reference.

Currently, the `Debug.xcconfig` file sets `KMP_BUILD_FLAVOR=dev` and the `Release.xcconfig` sets
`KMP_BUILD_FLAVOR=prod`. When adding new build configurations, please make sure to also define the
`KMP_BUILD_FLAVOR`
variable using the aforementioned method.

### Crashlytics

We can have symbolicated Kotlin crash reports on iOS. We
use [NSExceptionKt](https://github.com/rickclephas/NSExceptionKt) to achieve that.
Everything is set up, but some finishing touches need to be made when you add Crashlytics to your
project:

1. Set up Firebase Crashlytics on both platforms as you would usually do.
2. After dependencies are in place, on each platform uncomment the code in
   `PlatformFirebaseCrashlyticsImpl` classes (follow comments).
3. On iOS, do not forget to also upload debug symbols to Crashlytics. The KMP framework is static
   in Beta and Release builds, so no standalone debug symbols are generated for KMP, instead, they
   are included in the app itself.

## Deep Linking

Deep links are provided by each platform to common code and parsed using `DeepLinkResolver` class.
The (sample) app currently supports the following url scheme: `kmptemplate` and the following links:

- `kmptemplate://home` -- Opens Home tab with default stack.
- `kmptemplate://profile` -- Opens Profile tab with default stack.
- `kmptemplate://home/second` -- Opens SecondScreen in Home tab.
- `kmptemplate://home/third?arg={argument}` -- Opens ThirdScreen in Home tab with provided argument.
  The `argument` is mandatory.
- `kmptemplate://home/third/{argument}` -- Opens ThirdScreen in Home tab with provided argument. The
  `argument` is mandatory.
