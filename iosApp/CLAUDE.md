# iOS App — CLAUDE.md

iOS-specific conventions for the SwiftUI layer in `iosApp/`.

## Component + ComponentModel Pattern

Every screen and navigation view follows the Component + ComponentModel pattern:

1. **Protocol** (`*ComponentModelProtocol`) — defines the public interface (properties, actions)
2. **ComponentModel** (`@Observable final class`) — conforms to the protocol, wraps KMP component
3. **Component** (`struct *Component<Model: *Protocol>: View`) — generic SwiftUI view

```swift
// Protocol
protocol MyComponentModelProtocol: AnyObject {
    var title: String { get }
    var alert: AlertModel? { get set }

    func onAction()
}

// ComponentModel
@Observable
final class MyComponentModel: MyComponentModelProtocol { ... }

// View
struct MyComponent<Model: MyComponentModelProtocol>: View {
    @State var model: Model
    ...
}
```

Screen-level protocols use `AnyObject` constraint. Navigation-level protocols do not.

### KMP Deviation Comment

Screen-level ComponentModel protocols include this doc comment:

```swift
/// KMP deviation: Conforms to `AnyObject` instead of FuturedKit's `ComponentModel` protocol
/// because navigation and event handling are managed by KMP Decompose, not a Swift Coordinator.
```

### Debug Mocks

Each screen ComponentModel has a `#if DEBUG` mock for SwiftUI previews:

```swift
#if DEBUG
@Observable
final class MyComponentModelMock: MyComponentModelProtocol {
    var title = "Preview Title"
    var alert: AlertModel?
    func onAction() {}
}
#endif
```

## Bindings to @State Model Properties

When creating a `Binding` to a property on an `@State` model, use the `$` projection syntax — never use verbose `Binding(get:set:)`:

```swift
// Correct
TabView(selection: $model.selectedTab) { ... }
.sheet(item: $model.sheetItem) { ... }
.alert("", isPresented: $model.alert.isPresented) { ... }

// Wrong
TabView(selection: Binding(
    get: { model.selectedTab },
    set: { model.onTabSelected($0) }
)) { ... }
```

To make a property bindable, expose it with a getter/setter in the ComponentModel:

```swift
var selectedTab: NavigationTab {
    get { viewState.selectedTab }
    set { actions.onTabSelected(tab: newValue) }
}
```

## Slot Binding Dismiss Actions

Every Decompose slot binding used with `.sheet(item:)` or `.fullScreenCover(item:)` **must** call a KMP dismiss action in its setter — never leave it empty. Without this, SwiftUI swipe-to-dismiss does not notify KMP, leaving stale slot state.

```swift
// In ComponentModel
var sheetItem: DecomposeSlotItem<SomeSheetChild>? {
    get {
        guard let child = _sheet.child else { return nil }
        return DecomposeSlotItem(id: ObjectIdentifier(child), instance: child.instance)
    }
    set { // swiftlint:disable:this unused_setter_value
        actions.onSheetDismissed()
    }
}
```

## Navigation ComponentModel Structure

Navigation ComponentModels use MARK comments to organize sections:

```swift
@Observable
final class MyNavigationComponentModel: MyNavigationComponentModelProtocol {

    // MARK: Public computed properties
    ...

    // MARK: Private stored properties
    ...

    // MARK: Private @ObservationIgnored properties
    ...

    // MARK: Init / Deinit
    ...

    // MARK: Public functions
    ...
}
```

Views never access `model.actions` directly — all actions are wrapped in model methods or computed property setters.

## View Modifier Ordering

Screen-level views apply modifiers in this order:

```swift
var body: some View {
    content
        .background(...)                    // 1. Appearance
        .navigationTitle(...)               // 2. Navigation setup
        .navigationBarTitleDisplayMode(.inline)
        .toolbar { ... }
        .onChange(of: ...) { ... }          // 3. Event handling
}
```

Not every modifier is present on every screen — only include what the screen needs, but keep the relative order consistent.

For screens with complex content, extract a `contentView` computed property and apply modifiers to it in `body`.

## Localization

All user-facing strings **must** use KMP localization keys from `Localizable` — never hardcode text directly. The KMP module (`import KMP`) exposes `Localizable` with `.localized` for accessing translated strings from `strings.xml`.

```swift
// Correct
Text(Localizable.first_screen_title.localized)
Button(Localizable.first_screen_button.localized, action: model.onNext)

// Wrong — hardcoded string
Text("First Screen")
```

## Icons

All icons **must** come from KMP shared resources (`Images.ic_*`) — never add new icons to `Assets.xcassets`. The KMP module is the single source of truth for icons shared across platforms.

- Use `.templateImage` for monochrome icons that should adopt the current foreground style (menus, toolbars, list rows)
- Use `.image` only for non-icon resources (logos, illustrations) that should render with original colors

```swift
// Correct — KMP icon with template rendering
Images.ic_trash.templateImage

// Wrong — adding icons to xcassets
Image(systemName: "trash")
```

## Struct Property Access Control

When a struct has a custom `init`, all stored properties that are not accessed from external call sites **must** be marked `private`. The custom `init` provides the public API — internal properties should not leak.

```swift
// Correct
struct MyCard<Content: View>: View {
    private let title: String
    @ViewBuilder private let content: () -> Content

    init(title: String, @ViewBuilder content: @escaping () -> Content) {
        self.title = title
        self.content = content
    }
}
```

## Tap Target Hit Areas

Empty whitespace inside a `Button` or tap gesture — from `Spacer()`, `.frame(maxWidth: .infinity)`, padding, etc. — is not tappable by default. Apply `.contentShape(.rect)` on the container to make the entire frame respond to taps:

```swift
Button(action: onTap) {
    HStack {
        Text("Label")
        Spacer()
    }
    .frame(maxWidth: .infinity)
    .contentShape(.rect)
}
```

## Pull Request Labels

When creating a PR, always add labels based on which areas of the codebase are affected:

- **iOS** — changes in `iosApp/`
- **KMP** — changes in `shared/`
- **Android** — changes in `androidApp/`

Multiple labels can (and should) be applied when the PR spans several areas.

<!-- ============================================================
     PROJECT-SPECIFIC SECTIONS
     Fill these in when initializing a new project with `claude code init`.
     Remove the HTML comments and populate with your project's specifics.
     ============================================================ -->

<!-- ## Screen Pattern

Describe how screen-level views structure their body (e.g., wrapping content
in a `ComponentStateView`, handling empty/loading/error states).

```swift
var body: some View {
    ComponentStateView(state: model.componentState) {
        screenContent
    }
}
```
-->

<!-- ## Tab Bar Visibility

If the project uses tab-based navigation with hidden tab bars on certain screens,
describe how `TabBarConfiguration` works and where to add new cases.
-->

<!-- ## Reusable View Config Pattern

List the project's reusable UI components and their config structs:

| Component | Config struct | Location |
|---|---|---|
| `AppButton` | `ButtonConfig` | `UI/Views/AppButton.swift` |
-->

<!-- ## Alerts

Describe the alert presentation pattern used in this project
(e.g., `AlertModel`, `AlertConfig`, `UiDialog` conversion).
-->

<!-- ## Design System

Describe the project's design system tokens (colors, typography, spacing, shapes).
-->

<!-- ## Jira / Ticket Context

When connected to a project tracker:
1. Extract issue IDs from branch name (format: `feature/PROJ-XXX-...`)
2. Fetch subtask + parent story for full context
3. Use requirements to guide implementation
-->
