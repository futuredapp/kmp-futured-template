package app.futured.horizontalpagerbug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class NavScreen { HOME, PAGER, DUMMY }

@Composable
fun App() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
            var screen by remember { mutableStateOf(NavScreen.HOME) }
            val onBack = { screen = NavScreen.HOME }
            BackHandlerEffect(enabled = screen != NavScreen.HOME, onBack = onBack)
            when (screen) {
                NavScreen.HOME -> HomeScreen(
                    onNavigateToPager = { screen = NavScreen.PAGER },
                    onNavigateToDummy = { screen = NavScreen.DUMMY },
                )
                NavScreen.PAGER -> PagerScreen()
                NavScreen.DUMMY -> DummyScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToPager: () -> Unit, onNavigateToDummy: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = onNavigateToPager) {
                Text("CMP Pager Screen (broken)")
            }
            Button(onClick = onNavigateToDummy) {
                Text("CMP Dummy Screen (working back gesture)")
            }
        }
    }
}

@Composable
fun PagerScreen() {
    val pageColors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Swipe horizontally to change pages.\nOn iOS 26, horizontal swipe triggers the back gesture instead.",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
        )

        val pagerState = rememberPagerState { pageColors.size }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(pageColors[page].copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Page ${page + 1}",
                    style = MaterialTheme.typography.headlineLarge,
                )
            }
        }

        Text(
            text = "Current page: ${pagerState.currentPage + 1} / ${pageColors.size}",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun DummyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Dummy Screen",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp),
            )
            Text(
                text = "Back gesture works here.\nNo HorizontalPager to fight for the swipe.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }
    }
}
