package app.futured.horizontalpagerbug

import androidx.compose.ui.window.ComposeUIViewController

fun HomeViewController(onNavigateToPager: () -> Unit, onNavigateToDummy: () -> Unit) = ComposeUIViewController {
    HomeScreen(onNavigateToPager = onNavigateToPager, onNavigateToDummy = onNavigateToDummy)
}

fun PagerViewController() = ComposeUIViewController { PagerScreen() }

fun DummyViewController() = ComposeUIViewController { DummyScreen() }
