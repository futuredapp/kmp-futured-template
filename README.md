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
- minSdk: ~~`28`~~
- targetSdk: ~~`34`~~
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

## Kotlin Multiplatform build in iOS

The iOS application target depends on a local Swift Package, which wraps an XCFramework generated by
KMP Gradle Plugin.
There is a dedicated "KMP Package" build target in XCode, which should be used to build KMP package
locally. Here is its [Makefile](iosApp/shared/KMP/Makefile). This Makefile detects current
build type and build flavor from current [build configuration](#ios-1) and runs one of
`assembleAndCopyDebugSwiftPackage` and `assembleAndCopyReleaseSwiftPackage` Gradle tasks with
all necessary parameters.

## Navigation Structure

The app utilizes [Decompose](https://arkivanov.github.io/Decompose/) to share presentation logic and
navigation state in KMP.  
The following meta-description provides an overview of Decompose navigation tree:

```kotlin
Navigation("RootNavHost") {
    Slot {
        Screen("LoginScreen")
        Navigation("SignedInNavHost") {
            // Bottom navigation stack
            Stack {
                // Home tab
                Navigation("HomeNavHost") {
                    Stack {
                        Screen("FirstScreen")
                        Screen("SecondScreen") {
                            Slot {
                                Screen("Picker")
                            }
                        }
                        Screen("ThirdScreen")
                    }
                }
                // Profile tab
                Navigation("ProfileNavHost") {
                    Stack {
                        Screen("ProfileScreen")
                    }
                }
            }
        }
    }
}
```

## Project Setup

### Initial script

Use `init_template.kts` script to set up the template.
The script renames directories and package names in files to the given package name.

It is written in Kotlin. To run it you need to have [kscript](https://github.com/kscripting/kscript)
installed.

#### Usage

```shell
kscript init_template.kts
```

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

This variable is then used in the shared framework build step to pass the flavor as Gradle build
flag:

```shell
./gradlew :shared:app:embedAndSignAppleFrameworkForXcode -P buildkonfig.flavor=KMP_BUILD_FLAVOR
```

Currently, the `Debug` build configuration uses the `dev` flavor, and the `Release` configuration
uses the `prod` flavor.
When adding new build configurations, please make sure to also define the `KMP_BUILD_FLAVOR`
variable using the aforementioned method.

### Crashlytics

We can have symbolicated Kotlin crash reports on iOS. We
use [NSExceptionKt](https://github.com/rickclephas/NSExceptionKt) to achieve that.
Everything is set up, but some finishing touches need to be made when you add Crashlytics to your
project:

1. Set up Firebase Crashlytics on both platforms as you would usually do.
2. After dependencies are in place, on each platform uncomment the code in
   `PlatformFirebaseCrashlyticsImpl` classes (follow comments).
3. On iOS, do not forget to also upload debug symbols to Crashlytics. The KMP framework is static,
   so no standalone debug symbols are generated for KMP, instead, they are included in the app
   itself.

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
