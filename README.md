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
