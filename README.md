# Project Name

![kmp](https://img.shields.io/badge/multiplatform-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![compose](https://img.shields.io/badge/jetpack_compose-2bab6b.svg?style=for-the-badge&logo=android&logoColor=white)
![swiftui](https://img.shields.io/badge/swiftui-%23000000.svg?style=for-the-badge&logo=swift&logoColor=white)

![kotlin-version](https://img.shields.io/badge/kotlin-1.9.10-%237F52FF.svg?style=flat-square&logo=kotlin&logoColor=white)
![android-minsdk](https://img.shields.io/badge/minsdk-29-2bab6b.svg?style=flat-square&logo=android&logoColor=white)
![android-targetsdk](https://img.shields.io/badge/targetsdk-34-2bab6b.svg?style=flat-square&logo=android&logoColor=white)
![ios-target](https://img.shields.io/badge/target-16.0-%23000000.svg?style=flat-square&logo=apple&logoColor=white)

Project description.

## Project info

- ApplicationId: `app.futured.kmptemplate`
- Design: Figma (add link)
- Backend: (REST / GraphQL) (add specification)
- Use-Cases: Kotlin Coroutines [cr-usecases](https://github.com/futuredapp/arkitekt)

### Team:

- TODO

## Project Setup

### Product Flavors

The project utilizes [BuildKonfig](https://github.com/yshrsmz/BuildKonfig) plugin to achieve build flavors in network module.
There are two product flavors: `dev` and `prod`, which select API url used in `:shared:network:rest` and `:shared:network:graphql` modules.

In general, the build flavor can be specified as a Gradle build flag
```shell
./gradlew whateverTask -P buildkonfig.flavor=dev
```

Please, refer to `:shared:network:*` module Gradle config.

#### Android

During local development, the build flavor can be specified in `gradle.properties` file like so:
```properties
buildkonfig.flavor=dev
```

#### iOS

On iOS, we utilize .xcconfig [Build Configuration](https://www.kodeco.com/21441177-building-your-app-using-build-configurations-and-xcconfig) files,
where each file per build configuration specifies a `KMM_FLAVOR` environment variable.

This variable is then used in shared framework build step to pass the flavor as Gradle build flag:
```shell
./gradlew :shared:app:embedAndSignAppleFrameworkForXcode -P buildkonfig.flavor=$KMM_FLAVOR
```

Currently, the `Debug` build configuration uses `staging` flavor and `Release` configuration uses `prod` flavor.
When adding new build configurations, please make sure to also define the `KMM_FLAVOR` variable using the aforementioned method.

### Crashlytics

We can have symbolicated Kotlin crash reports on iOS.
We [NSExceptionKt](https://github.com/rickclephas/NSExceptionKt) to achieve that.
Everything is set up, but some finishing touches need to be made when you add Crashlytics to your project:

1. Set up Firebase Crashlytics on both platforms as you would usually do.
2. After dependencies are in place, on each platform uncomment the code in `PlatformFirebaseCrashlyticsImpl` classes (follow comments).
3. On iOS, do not forget to also upload debug symbols from KMP framework. We have to do this manually. To do this, set up additional build phase in Xcode:

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

## Used Tools

- Code style - **[ktlint](https://ktlint.github.io/)**, **[detekt](https://arturbosch.github.io/detekt/)**, **[Android lint](http://tools.android.com/tips/lint)**, **[Danger](https://github.com/futuredapp/danger)**
- Kotlin -> Swift interop - **[SKIE]**(https://skie.touchlab.co/)

## Test accounts

- TODO

## Kotlin Gradle tasks

1. `clean` - Remove all `build` folders
2. `lintCheck` - Run `ktlint`, `detekt` checks. Same runs on CI.
3. `ktlintFormat` - Reformat source code according to ktlint rules
5. `:shared:network:graphql:downloadApolloSchemaFromIntrospection` - Download latest Apollo schema
6. `:shared:network:graphql:generateApolloSources` - Generate Apollo sources (rebuilds models after adding modifying queries, mutations, etc.)
