package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.android.ui.screen.LoginScreenUi
import app.futured.kmptemplate.feature_v3.navigation.root.RootChild
import app.futured.kmptemplate.feature_v3.navigation.root.RootConfig
import app.futured.kmptemplate.feature_v3.navigation.root.RootNavHost
import com.arkivanov.decompose.router.slot.ChildSlot

@Composable
fun RootNavHostUi(
    navHost: RootNavHost,
    modifier: Modifier = Modifier,
) {
    val slot: ChildSlot<RootConfig, RootChild> by navHost.slot.collectAsStateWithLifecycle()

    Box(modifier.background(MaterialTheme.colorScheme.background)) {
        when (val childInstance = slot.child?.instance) {
            is RootChild.Login -> LoginScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
            is RootChild.SignedIn -> SignedInNavHostUi(navHost = childInstance.navHost, modifier = Modifier.fillMaxSize())
            null -> ApplicationLoading(Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun ApplicationLoading(
    modifier: Modifier = Modifier,
) = Box(modifier.background(MaterialTheme.colorScheme.background)) {
    CircularProgressIndicator(
        Modifier
            .size(48.dp)
            .align(Alignment.Center),
    )
}
