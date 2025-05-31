package app.futured.kmptemplate.android.ui.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.android.ui.screen.WelcomeScreenUi
import app.futured.kmptemplate.feature.navigation.root.RootChild
import app.futured.kmptemplate.feature.navigation.root.RootConfig
import app.futured.kmptemplate.feature.navigation.root.RootNavHost
import com.arkivanov.decompose.router.slot.ChildSlot

@Composable
fun RootNavHostUi(
    navHost: RootNavHost,
    modifier: Modifier = Modifier,
) {
    val slot: ChildSlot<RootConfig, RootChild> by navHost.slot.collectAsStateWithLifecycle()

    Box(modifier.background(MaterialTheme.colorScheme.background)) {
        when (val childInstance = slot.child?.instance) {
            is RootChild.Intro -> WelcomeScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
            is RootChild.Home -> HomeNavHostUi(
                navHost = childInstance.navHost,
                modifier = Modifier.fillMaxSize(),
            )
            null -> ApplicationLoading(Modifier.fillMaxSize(), navHost.actions::updateCameraPermission)
        }
    }
}

@Composable
private fun ApplicationLoading(
    modifier: Modifier = Modifier,
    updateCameraPermission: (Boolean) -> Unit,
) = Box(modifier.background(MaterialTheme.colorScheme.background)) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val permissionResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        updateCameraPermission(permissionResult == PackageManager.PERMISSION_GRANTED)
    }

    CircularProgressIndicator(
        Modifier
            .size(48.dp)
            .align(Alignment.Center),
    )
}
