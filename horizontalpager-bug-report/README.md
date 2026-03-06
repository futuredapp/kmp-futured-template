# HorizontalPager iOS 26 Back-Gesture Bug — Reproduction

## Bug description

On **iOS 26**, swiping horizontally anywhere on screen inside a `HorizontalPager` (Compose Multiplatform)
triggers the system **back gesture** instead of scrolling between pager pages.

This is caused by iOS 26 expanding the back swipe gesture area to the **entire screen width**, not just
the leading edge as on iOS 17/18. Any horizontal swipe — even in the center of the screen — is interpreted
as a back gesture, making `HorizontalPager` completely unusable when pushed inside a `NavigationStack`.

**Expected:** swiping left/right above the horizontal pager scrolls between pager pages.

**Actual:** swiping left/right triggers the iOS back navigation gesture, dismissing the screen.

## Steps to reproduce

1. Open the app on **iOS 26** (simulator or device).
2. Tap **"CMP Pager Screen"** — this pushes the pager screen onto a native `NavigationStack`.
3. Try to swipe horizontally to scroll pages left and right.

**Result on iOS 26:** Swipe to the previous page dismisses the screen instead of changing pages in the pager.
**Result on iOS 17/18:** Works correctly — pager handles scroll as expected.

## Root cause

iOS 26 changed the back gesture recognition area from edge-only to full-screen width. When a
`ComposeUIViewController` containing a `HorizontalPager` is pushed onto a `NavigationStack`,
iOS 26's full-screen back gesture takes priority over Compose's horizontal pan gesture, making
it impossible to swipe between pager pages.

## Environment

- Compose Multiplatform: 1.11.2
- Kotlin: 2.3.10
- iOS: 26.2 (confirmed broken)

---

## Building

### Android

```
./gradlew :composeApp:assembleDebug
```

### iOS

```
cd iosApp && xcodegen generate
```

Then open `iosApp/iosApp.xcodeproj` in Xcode and run on an iOS 26 simulator or device.
