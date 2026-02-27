# Plan: Create shared/ui Compose Multiplatform Module

## Context
All Android Compose screens currently live in `androidApp`. The goal is to create a new `shared/ui` KMP module with Compose Multiplatform (CMP) so screens work on both Android and iOS via `ComposeUIViewController`. The app theme and animation provider also move to the shared module. iOS currently uses native SwiftUI with ViewModel wrappers — these are replaced with UIViewControllerRepresentable wrappers around Kotlin Compose view controllers.

---

## Critical Files

| File | Action |
|------|--------|
| `gradle/libs.versions.toml` | Add CMP plugin alias + ui namespace version |
| `settings.gradle.kts` | Register `:shared:ui` |
| `build.gradle.kts` (root) | Declare `compose.multiplatform` plugin (apply false) |
| `shared/ui/build.gradle.kts` | **CREATE** — new CMP module |
| `shared/ui/src/commonMain/…` | **CREATE** — theme + screens + Showcase |
| `shared/ui/src/iosMain/…` | **CREATE** — ViewControllers.ios.kt |
| `shared/app/build.gradle.kts` | Export `projects.shared.ui` in XCFramework |
| `androidApp/build.gradle.kts` | Add `projects.shared.ui` dependency |
| `androidApp/src/main/…` | Delete moved files; update 4 nav hosts + MainActivity |
| `iosApp/…/Views/Screen/*/` | Replace native View+ViewModel files with Compose wrappers |
| `iosApp/…/Views/Navigation/` | Update 3 nav files to drop ViewModel wrapping |

---

## Implementation Steps

### Step 1 — `gradle/libs.versions.toml`

Add CMP Gradle plugin alias (same version as `jetbrainsComposeRuntime = "1.10.1"`):
```toml
# under [plugins]
compose-multiplatform = { id = "org.jetbrains.compose", version.ref = "jetbrainsComposeRuntime" }

# under [versions]
project-shared-ui-namespace = "app.futured.kmptemplate.ui.android"
```

### Step 2 — `settings.gradle.kts`

```kotlin
include(":shared:ui")
```

### Step 3 — `build.gradle.kts` (root)

```kotlin
alias(libs.plugins.compose.multiplatform) apply false
```

### Step 4 — `shared/ui/build.gradle.kts` (CREATE)

```kotlin
import app.futured.kmptemplate.gradle.configuration.ProjectSettings

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)

    id(libs.plugins.conventions.lint.get().pluginId)
}

kotlin {
    jvmToolchain(ProjectSettings.Kotlin.JvmToolchainVersion)
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    android {
        namespace = libs.versions.project.shared.ui.namespace.get()
        compileSdk = ProjectSettings.Android.CompileSdkVersion
        minSdk = ProjectSettings.Android.MinSdkVersion
    }

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.shared.feature)
                implementation(projects.shared.arkitektDecompose)
                implementation(projects.shared.kmpResources)

                implementation(compose.material3)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.animation)
                implementation(compose.materialIconsExtended)
                implementation(compose.uiToolingPreview)

                implementation(libs.decompose)
                implementation(libs.decompose.compose.ext)
                implementation(libs.kotlinx.immutableCollections)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.lifecycle.compose)
            }
        }

        androidMain {
            dependencies {
                implementation(compose.uiTooling)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
```

### Step 5 — Create `shared/ui/src/commonMain/kotlin/app/futured/kmptemplate/ui/`

#### 5a. `AppTheme.kt`
Moved from `androidApp/.../MyApplicationTheme.kt`. Rename `MyApplicationTheme` → `AppTheme`. Change import of `TemplateStackAnimationProvider` to local package (no longer `android.ui.theme`). `isSystemInDarkTheme()` is CMP-compatible.

#### 5b. `theme/TemplateStackAnimationProvider.kt`
Moved from `androidApp/.../ui/theme/TemplateStackAnimationProvider.kt`. Package: `app.futured.kmptemplate.ui.theme`. No changes to code.

#### 5c. `theme/Constants.kt`
Moved from `androidApp/.../tools/Constants.kt`. Package: `app.futured.kmptemplate.ui.theme`.

#### 5d. `components/Showcase.kt`
Moved from `androidApp/.../ui/components/Showcase.kt`. Update import: `MyApplicationTheme` → `AppTheme` from `app.futured.kmptemplate.ui`.

#### 5e–5k. All screen files: `screen/FirstScreenUi.kt`, `SecondScreenUi.kt`, `ThirdScreenUi.kt`, `ProfileScreenUi.kt`, `LoginScreenUi.kt`, `PickerScreenUi.kt`, `_TemplateScreenUi.kt`

Package: `app.futured.kmptemplate.ui.screen`

**Changes for each screen:**
- Package: `app.futured.kmptemplate.android.ui.screen` → `app.futured.kmptemplate.ui.screen`
- Remove `import app.futured.kmptemplate.android.ui.components.Showcase`; add `import app.futured.kmptemplate.ui.components.Showcase`
- Replace `import androidx.compose.ui.tooling.preview.Preview` → `import org.jetbrains.compose.ui.tooling.preview.Preview`
- Replace `import androidx.compose.ui.tooling.preview.PreviewParameter` → `import org.jetbrains.compose.ui.tooling.preview.PreviewParameter`
- Replace `import androidx.compose.ui.tooling.preview.PreviewParameterProvider` → `import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider`
- Replace `import androidx.compose.runtime.collectAsState` → `import androidx.lifecycle.compose.collectAsStateWithLifecycle` (where applicable)

**FirstScreenUi.kt specific changes:**
- Remove `import android.widget.Toast` and `import androidx.compose.ui.platform.LocalContext`
- Replace Toast-based event handling with a Compose Snackbar:
  ```kotlin
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  EventsEffect(eventsFlow = screen.events) {
      onEvent<FirstUiEvent.ShowToast> { event ->
          scope.launch { snackbarHostState.showSnackbar(event.text.localized()) }
      }
  }

  Scaffold(
      snackbarHost = { SnackbarHost(snackbarHostState) },
      ...
  )
  ```
  `event.text.localized()` works in commonMain via `import app.futured.kmptemplate.resources.localized`.

### Step 6 — `shared/ui/src/iosMain/kotlin/app/futured/kmptemplate/ui/ViewControllers.ios.kt` (CREATE)

```kotlin
@file:Suppress("FunctionNaming")

package app.futured.kmptemplate.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreen
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreen
import app.futured.kmptemplate.feature.ui.loginScreen.LoginScreen
import app.futured.kmptemplate.feature.ui.profileScreen.ProfileScreen
import app.futured.kmptemplate.feature.ui.picker.PickerScreen
import app.futured.kmptemplate.ui.screen.*

fun FirstUiController(screen: FirstScreen) = ComposeUIViewController {
    AppTheme { FirstScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun SecondUiController(screen: SecondScreen) = ComposeUIViewController {
    AppTheme { SecondScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun ThirdUiController(screen: ThirdScreen) = ComposeUIViewController {
    AppTheme { ThirdScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun LoginUiController(screen: LoginScreen) = ComposeUIViewController {
    AppTheme { LoginScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun ProfileUiController(screen: ProfileScreen) = ComposeUIViewController {
    AppTheme { ProfileScreenUi(screen = screen, modifier = Modifier.fillMaxSize()) }
}

fun PickerUiController(screen: PickerScreen) = ComposeUIViewController {
    AppTheme { PickerScreenUi(pickerScreen = screen, modifier = Modifier.fillMaxSize()) }
}
```

### Step 7 — `shared/app/build.gradle.kts`

In the `iosMain` dependencies and XCFramework exports, add:
```kotlin
// In forEach { it.binaries.framework { ... } }:
export(projects.shared.ui)

// In iosMain dependencies:
api(projects.shared.ui)
```

### Step 8 — `androidApp/build.gradle.kts`

Add to `dependencies { }`:
```kotlin
implementation(projects.shared.ui)
```

Remove `implementation(platform(libs.androidx.compose.bom))` and `implementation(libs.bundles.compose)` only if all compose usage migrates — **keep these for now** since androidApp still uses navigation-layer compose (ModalBottomSheet, etc.).

### Step 9 — Delete from `androidApp/src/main/kotlin/app/futured/kmptemplate/android/`

- `MyApplicationTheme.kt`
- `ui/theme/TemplateStackAnimationProvider.kt`
- `ui/theme/Constants.kt` (actually `tools/Constants.kt`)
- `ui/components/Showcase.kt`
- `ui/screen/FirstScreenUi.kt`
- `ui/screen/SecondScreenUi.kt`
- `ui/screen/ThirdScreenUi.kt`
- `ui/screen/ProfileScreenUi.kt`
- `ui/screen/LoginScreenUi.kt`
- `ui/screen/PickerScreenUi.kt`
- `ui/screen/_TemplateScreenUi.kt`

### Step 10 — Update androidApp navigation files

**`HomeNavHostUi.kt`** — update screen imports:
- `import app.futured.kmptemplate.android.ui.screen.{First,Second,Third,Picker}ScreenUi`
  → `import app.futured.kmptemplate.ui.screen.{First,Second,Third,Picker}ScreenUi`

**`RootNavHostUi.kt`** — update import:
- `import app.futured.kmptemplate.android.ui.screen.LoginScreenUi`
  → `import app.futured.kmptemplate.ui.screen.LoginScreenUi`

**`ProfileNavHostUi.kt`** — update imports:
- `import app.futured.kmptemplate.android.ui.screen.{Profile,Third}ScreenUi`
  → `import app.futured.kmptemplate.ui.screen.{Profile,Third}ScreenUi`

### Step 11 — `androidApp/.../MainActivity.kt`

- `import app.futured.kmptemplate.android.MyApplicationTheme` → `import app.futured.kmptemplate.ui.AppTheme`
- `MyApplicationTheme {` → `AppTheme {`

### Step 12 — iOS: Replace native screen views with Compose wrappers

For each screen folder, **delete** the existing `*View.swift` and `*ViewModel.swift` files and create a new `*View.swift` with this pattern (using `FirstView.swift` as example):

```swift
import KMP
import SwiftUI

struct FirstComposeView: UIViewControllerRepresentable {
    private let screen: FirstScreen
    init(_ screen: FirstScreen) { self.screen = screen }
    func makeUIViewController(context: Context) -> some UIViewController {
        FirstUiController(screen: screen)
    }
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
}

struct FirstView: View {
    private let screen: FirstScreen
    init(_ screen: FirstScreen) { self.screen = screen }
    var body: some View {
        FirstComposeView(screen).ignoresSafeArea()
    }
}
```

Apply this pattern for: `FirstView.swift`, `SecondView.swift`, `ThirdView.swift`, `LoginView.swift`, `ProfileView.swift`, `PickerView.swift`.

Delete the corresponding `*ViewModel.swift` files: `FirstViewModel.swift`, `SecondViewModel.swift`, `ThirdViewModel.swift`, `LoginViewModel.swift`, `ProfileViewModel.swift`, `PickerViewModel.swift`.

### Step 13 — iOS: Update navigation files

**`HomeTabNavigationView.swift`** — drop ViewModel wrapping:
```swift
case .first(let entry):   FirstView(entry.screen)
case .second(let entry):  SecondView(entry.screen)
case .third(let entry):   ThirdView(entry.screen)
// sheet:
case .picker(let instance): PickerView(instance.screen)
    .presentationDetents(.init([.medium]))
```

**`RootNavigationView.swift`**:
```swift
case .login(let entry):    LoginView(entry.screen).id(entry.iosViewId)
```

**`ProfileTabNavigationView.swift`**:
```swift
case .profile(let entry):  ProfileView(entry.screen)
case .third(let entry):    ThirdView(entry.screen)
```

---

## Verification

1. **Android compilation:**
   ```bash
   ./gradlew androidApp:compileDebugKotlin
   ```

2. **Full Android build:**
   ```bash
   ./gradlew androidApp:assembleDebug
   ```

3. **iOS XCFramework:**
   ```bash
   ./gradlew assembleAndCopyDebugSwiftPackage
   ```

4. **iOS build in Xcode** — open `iosApp/iosApp.xcodeproj`, build for simulator. Verify all screens display Compose UI.

5. **Android runtime** — run the app, verify screens look correct, toast still appears as Snackbar on First screen.
