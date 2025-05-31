@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.android.ui.screen

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.arkitekt.decompose.event.onEvent
import app.futured.kmptemplate.android.tools.utils.saveAndShareImage
import app.futured.kmptemplate.android.ui.components.dialog.MDevDialog
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdScreen
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdUIEvent
import app.futured.kmptemplate.feature.ui.thirdScreen.ThirdViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.asPainterResource
import app.futured.kmptemplate.resources.kmpStringResource
import app.futured.kmptemplate.resources.localized
import app.futured.mdevcamp.android.ui.components.button.ButtonPrimary
import app.futured.mdevcamp.android.ui.components.layout.VerticalSpacer
import app.futured.mdevcamp.android.ui.theme.Grid
import app.futured.mdevcamp.android.ui.theme.MDevTheme
import coil.compose.AsyncImage

@Composable
fun ThirdScreenUi(
    screen: ThirdScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle()

    Content(viewState = viewState, actions = actions, modifier = modifier)

    val activity = LocalContext.current as ComponentActivity
    val coroutineScope = rememberCoroutineScope()
    EventsEffect(eventsFlow = screen.events) {
        onEvent<ThirdUIEvent.Share> {
            activity.saveAndShareImage(
                imageUrl = viewState.avatar.image,
                coroutineScope = coroutineScope,
                onLoadingStarted = {
                    actions.onShareAvatarLoadingStarted()
                },
                onLoadingFinished = {
                    actions.onShareAvatarLoadingFinished()
                },
                onError = {
                    Toast.makeText(activity, "Ouch. Try again, later!", Toast.LENGTH_SHORT).show()
                },
            )
        }
    }

    viewState.uiDialog?.let { dialog ->
        MDevDialog(uiDialog = dialog)
    }
}

@Composable
private fun Content(
    viewState: ThirdViewState,
    actions: ThirdScreen.Actions,
    modifier: Modifier = Modifier,
) {
    val colors = MDevTheme.colors

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = actions::onBack,
                colors = IconButtonDefaults.iconButtonColors(contentColor = colors.white),
            ) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }

            AnimatedContent(targetState = viewState.shareAvatarImageLoading, label = "") { isLoading ->
                if (!isLoading) {
                    IconButton(
                        onClick = actions::onShare,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = colors.white),
                    ) {
                        Icon(painter = MR.images.ic_share.asPainterResource(), contentDescription = null)
                    }
                } else {
                    Box(modifier = Modifier.padding(top = Grid.d4, end = Grid.d3)) {
                        CircularProgressIndicator(
                            color = MDevTheme.colors.white,
                            modifier = Modifier.size(Grid.d4),
                            strokeWidth = Grid.d0_5,
                        )
                    }
                }
            }
        }

        VerticalSpacer(size = Grid.d8)

        AsyncImage(
            model = viewState.avatar.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            alignment = Alignment.TopCenter,
            filterQuality = FilterQuality.High,
        )

        VerticalSpacer(size = Grid.d6)
    }
}
