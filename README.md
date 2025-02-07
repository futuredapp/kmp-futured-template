# Futured KMP Template

Hey there 👋

This is our template from which we build Kotlin Multiplatform applications that target Android and iOS platforms.
It is our opinionated way of building KMP apps and shines a light on how we structure our architecture and what tools and libraries we use.

To give you a short overview of our stack, we use:

- Native UI on both platforms. Jetpack Compose on Android and SwiftUI on iOS. The rest of the application is shared in KMP.
- [Decompose](https://github.com/arkivanov/Decompose) for sharing presentation logic and navigation state.
- The presentation layer follows the MVI-like design pattern.
- [Koin](https://insert-koin.io/) for dependency injection.
- [SKIE](https://skie.touchlab.co/) for better Kotlin->Swift interop (exhaustive enums, sealed classes, Coroutines support).
- [moko-resources](https://github.com/icerockdev/moko-resources) for sharing string (and other types of) resources.
- [apollo-kotlin](https://github.com/apollographql/apollo-kotlin) network client for apps that call GraphQL APIs.
- [ktorfit](https://github.com/Foso/Ktorfit) network client for apps that call plain HTTP APIs.
- [Jetpack DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) as a simple preferences storage (we have JSON-based and primitive implementations).
- [iOS-templates](https://github.com/futuredapp/iOS-templates) as template which generates a new iOS scene using MVVM-C architecture.

The template is a sample app with several screens to let you kick off the project with everything set up, incl. navigation and some API calls.

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
- Dependency management: ~~**[Swift package manager](https://swift.org/package-manager/)**~~
- Command line tools: **[Fastlane](https://docs.fastlane.tools)**
- Code style:
  - **[SwiftLint](https://swift.org/package-manager/)**
  - **[Danger](https://github.com/futuredapp/danger)**

## Team:

- ~~Jana Nováková, PM, <jana.novakova@futured.app>~~
- ~~Jan Novák, iOS developer, <jan.novak@futured.app>~~
- ~~John Newman, tester, <john.newman@futured.app>~~

## Used Tools

- Code style - **[ktlint](https://ktlint.github.io/)**, **[detekt](https://arturbosch.github.io/detekt/)**, **[Android lint](http://tools.android.com/tips/lint)**, **[Danger](https://github.com/futuredapp/danger)**
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
4. `generateMRcommonMain` - Regenerate shared resource IDs. 
5. `:shared:network:graphql:downloadApolloSchemaFromIntrospection` - Download the latest Apollo schema
6. `:shared:network:graphql:generateApolloSources` - Generate Apollo sources (rebuilds models after adding modifying queries, mutations, etc.)

## Navigation Structure

The app utilizes [Decompose](https://arkivanov.github.io/Decompose/) to share presentation logic and navigation state in KMP.  
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

It is written in Kotlin. To run it you need to have [kscript](https://github.com/kscripting/kscript) installed.
#### Usage
```shell
kscript init_template.kts
```

### Product Flavors

The project utilizes [BuildKonfig](https://github.com/yshrsmz/BuildKonfig) plugin for implementing build flavors in the network module.
There are two product flavors: `dev` and `prod`, which select API url used in `:shared:network:rest` and `:shared:network:graphql` modules.

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

[Charles Proxy](https://www.charlesproxy.com/documentation/welcome/) should only be enabled for the dev api.

As of Android N, you need to add configuration to your app in order to have it trust the SSL certificates generated by Charles SSL Proxying.
This means that you can only use SSL Proxying with apps that you control.

Add a file `res/xml/network_security_config.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config xmlns:tools="http://schemas.android.com/tools">
    <base-config>
        <trust-anchors>
            <certificates src="user" tools:ignore="AcceptsUserCertificates"/>
            <certificates src="system"/>
        </trust-anchors>
    </base-config>
</network-security-config>
```

Add networkSecurityConfig into Dev/Enterprise Manifest 
```xml
<application android:networkSecurityConfig="@xml/network_security_config" />
```

#### iOS

On iOS, we utilize .xcconfig [Build Configuration](https://www.kodeco.com/21441177-building-your-app-using-build-configurations-and-xcconfig) files,
where each file per build configuration specifies a `KMP_FLAVOR` environment variable.

This variable is then used in the shared framework build step to pass the flavor as Gradle build flag:
```shell
./gradlew :shared:app:embedAndSignAppleFrameworkForXcode -P buildkonfig.flavor=$KMP_FLAVOR
```

Currently, the `Debug` build configuration uses the `dev` flavor, and the `Release` configuration uses the `prod` flavor.
When adding new build configurations, please make sure to also define the `KMP_FLAVOR` variable using the aforementioned method.

### Crashlytics

We can have symbolicated Kotlin crash reports on iOS.  We use [NSExceptionKt](https://github.com/rickclephas/NSExceptionKt) to achieve that.
Everything is set up, but some finishing touches need to be made when you add Crashlytics to your project:

1. Set up Firebase Crashlytics on both platforms as you would usually do.
2. After dependencies are in place, on each platform uncomment the code in `PlatformFirebaseCrashlyticsImpl` classes (follow comments).
3. On iOS, do not forget to also upload debug symbols from the KMP framework. This has to be done manually. To do this, set up an additional build phase in Xcode:

```shell
# https://firebase.google.com/docs/crashlytics/get-deobfuscated-reports?platform=ios&authuser=1#manually-upload-dsyms

# Location of GoogleService-Info.plist file for Firebase project (this might depend on build configuration)
GSPFILE="path_to_your_file.plist"

# `KOTLIN_FRAMEWORK_NAME` env variable should be defined in `.xcconfig` file for current build configuration.
# The other ones are implicitly provided by Xcode.
DSYMFILE="${SRCROOT}/../shared/app/build/xcode-frameworks/${CONFIGURATION}/${SDK_NAME}/${KOTLIN_FRAMEWORK_NAME}.framework.dSYM"

echo "Uploading Kotlin framework dSYM to Crashlytics"
echo "Google Services file: ${GSPFILE}"
echo "Shared framework dSYM file: ${DSYMFILE}"

# Validate
${BUILD_DIR%/Build/*}/SourcePackages/checkouts/firebase-ios-sdk/Crashlytics/upload-symbols --build-phase --validate -gsp ${GSPFILE} -p ios ${DSYMFILE}

# Upload
${BUILD_DIR%/Build/*}/SourcePackages/checkouts/firebase-ios-sdk/Crashlytics/upload-symbols -gsp ${GSPFILE} -p ios ${DSYMFILE}
```

## Deep Linking

Deep links are provided by each platform to common code and parsed using `DeepLinkResolver` class.
The (sample) app currently supports the following url scheme: `kmptemplate` and the following links:

- `kmptemplate://home` -- Opens Home tab with default stack.
- `kmptemplate://profile` -- Opens Profile tab with default stack.
- `kmptemplate://home/second` -- Opens SecondScreen in Home tab.
- `kmptemplate://home/third?arg={argument}` -- Opens ThirdScreen in Home tab with provided argument. The `argument` is mandatory.
- `kmptemplate://home/third/{argument}` -- Opens ThirdScreen in Home tab with provided argument. The `argument` is mandatory.
