package app.futured.horizontalpagerbug

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandlerEffect(enabled: Boolean, onBack: () -> Unit) {
    // No-op: back navigation is handled by SwiftUI NavigationStack
}