package app.futured.kmptemplate.android.ui.screen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.arkitekt.decompose.event.onEvent
import app.futured.kmptemplate.android.MyApplicationTheme
import app.futured.kmptemplate.android.R
import app.futured.kmptemplate.android.tools.intent.Intents
import app.futured.kmptemplate.android.ui.components.dialog.MDevDialog
import app.futured.kmptemplate.feature.ui.welcomeScreen.WelcomeScreen
import app.futured.kmptemplate.feature.ui.welcomeScreen.WelcomeUIEvent
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.asPainterResource
import app.futured.kmptemplate.resources.kmpStringResource
import app.futured.mdevcamp.android.ui.components.button.CircleButton
import app.futured.mdevcamp.android.ui.components.layout.HorizontalSpacer
import app.futured.mdevcamp.android.ui.components.layout.VerticalSpacer
import app.futured.mdevcamp.android.ui.theme.Grid
import app.futured.mdevcamp.android.ui.theme.MDevTheme

@Composable
fun WelcomeScreenUi(
    screen: WelcomeScreen,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val actions = screen.actions

    val viewState by screen.viewState.collectAsState()

    Content(actions = actions, modifier = modifier)

    EventsEffect(eventsFlow = screen.events) {
        onEvent<WelcomeUIEvent.OpenSystemSettings> {
            context.startActivity(Intents.systemSettings())
        }
    }

    viewState.uiDialog?.let { dialog ->
        MDevDialog(uiDialog = dialog)
    }
}

@Composable
private fun Content(
    actions: WelcomeScreen.Actions,
    modifier: Modifier = Modifier,
) {

    val colors = MDevTheme.colors
    val typography = MDevTheme.typography

    val bg = ImageBitmap.imageResource(id = R.drawable.ic_photo_icon_bg)

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        if (it) {
            actions.onCameraPermissionGranted()
        } else {
            actions.onCameraPermissionDenied()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = MDevTheme.dimensions.contentPadding),
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_camera_access),
            contentDescription = null,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = Grid.d6, bottom = Grid.d11)
                .align(Alignment.CenterHorizontally)
                .drawBehind {
                    val offsetX = (this.size.width / 2) - (bg.width / 2)
                    val offsetY = (this.size.height / 2) - (bg.height / 2)
                    drawImage(bg, srcOffset = IntOffset.Zero, dstOffset = IntOffset(offsetX.toInt(), offsetY.toInt()))
                },
        )

        VerticalSpacer(size = Grid.d2)
        Text(
            text = kmpStringResource(res = MR.strings.onboarding_camera_permission_title),
            style = typography.title2,
            color = colors.white,
        )
        VerticalSpacer(size = Grid.d13)
        HorizontalDivider(thickness = Dp.Hairline, color = colors.white20)
        VerticalSpacer(size = Grid.d3)

        Feature(
            title = kmpStringResource(res = MR.strings.onboarding_ai_avatar_title),
            description = kmpStringResource(res = MR.strings.onboarding_ai_avatar_description),
            image = R.drawable.ic_avatar,
        )

        VerticalSpacer(size = Grid.d3)
        HorizontalDivider(thickness = Dp.Hairline, color = colors.white20)
        VerticalSpacer(size = Grid.d6)
        Spacer(modifier = Modifier.weight(1f))

        CircleButton(
            icon = MR.images.ic_arrow.asPainterResource(),
            onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            },
            modifier = Modifier
                .padding(top = Grid.d6, bottom = Grid.d10)
                .align(Alignment.CenterHorizontally),
        )
    }
}


@Composable
fun Feature(title: String, description: String, image: Int, modifier: Modifier = Modifier) {
    val colors = MDevTheme.colors
    val typography = MDevTheme.typography
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = image), contentDescription = null, modifier = Modifier.size(Grid.d13))
        HorizontalSpacer(size = Grid.d5)
        Column {
            Text(
                text = title,
                style = typography.textNormalMedium,
                color = colors.white,
            )
            VerticalSpacer(size = Grid.d1)
            Text(
                text = description,
                style = typography.textNormalRegular,
                color = colors.white60,
            )
        }
    }
}


@Preview
@Composable
private fun FirstScreenPreview() {
    val actions = object : WelcomeScreen.Actions {
        override fun onCameraPermissionGranted() = Unit
        override fun onCameraPermissionDenied() = Unit
    }
    MyApplicationTheme {
        Surface {
            Content(
                actions = actions,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
