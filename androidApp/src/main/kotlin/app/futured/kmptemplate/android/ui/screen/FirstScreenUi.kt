@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.android.ui.screen

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.arkitekt.decompose.event.onEvent
import app.futured.kmptemplate.android.MyApplicationTheme
import app.futured.kmptemplate.android.R
import app.futured.kmptemplate.android.tools.compose.ComposeFileProvider
import app.futured.kmptemplate.android.tools.utils.readBytes
import app.futured.kmptemplate.android.ui.components.dialog.MDevDialog
import app.futured.kmptemplate.feature.model.ui.Avatar
import app.futured.kmptemplate.feature.model.ui.AvatarStatus
import app.futured.kmptemplate.feature.ui.firstScreen.FirstScreen
import app.futured.kmptemplate.feature.ui.firstScreen.FirstUiEvent
import app.futured.kmptemplate.feature.ui.firstScreen.FirstViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource
import app.futured.mdevcamp.android.ui.components.button.ButtonPrimary
import app.futured.mdevcamp.android.ui.components.layout.StateLayout
import app.futured.mdevcamp.android.ui.components.layout.StateLayoutDefaults
import app.futured.mdevcamp.android.ui.theme.Grid
import app.futured.mdevcamp.android.ui.theme.MDevTheme
import coil.compose.AsyncImage

@Composable
fun FirstScreenUi(
    screen: FirstScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Content(viewState = viewState, actions = actions, modifier = modifier)

    EventsEffect(eventsFlow = screen.events) {
        onEvent<FirstUiEvent.OpenSystemSettings> { }
    }

    viewState.uiDialog?.let { dialog ->
        MDevDialog(uiDialog = dialog)
    }
}

@Composable
private fun Content(
    viewState: FirstViewState,
    actions: FirstScreen.Actions,
    modifier: Modifier = Modifier,
) {

    val colors = MDevTheme.colors
    val typography = MDevTheme.typography

    val context = LocalContext.current
    val contentResolver = context.contentResolver
    var imageUri by remember {
        mutableStateOf(Uri.EMPTY)
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { result ->
        if (result) {
            readBytes(contentResolver, imageUri)?.let { actions.onRetakePhoto(imageUri.lastPathSegment.orEmpty(), it) }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) {
        if (it) {
            imageUri = ComposeFileProvider.getImageUri(context)
            cameraLauncher.launch(imageUri)
        } else {
            actions.onCameraPermissionDenied()
        }
    }

    Box(modifier = modifier.background(colors.background)) {
        Image(painter = painterResource(id = R.drawable.ic_header_bg), contentDescription = null, modifier = Modifier.scale(2f))
        StateLayout(
            isLoading = viewState.isLoading,
            error = viewState.error,
            errorView = {
                StateLayoutDefaults.Error(uiError = it, retryButton = kmpStringResource(res = MR.strings.try_again)) {
                    actions.onRetry()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(start = Grid.d5, end = Grid.d5, bottom = Grid.d10),
                horizontalArrangement = Arrangement.spacedBy(Grid.d2),
                verticalArrangement = Arrangement.spacedBy(Grid.d2),
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Row(
                        modifier = Modifier.padding(top = Grid.d3, bottom = Grid.d5),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {


                        TextButton(
                            onClick = {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            },
                            modifier = Modifier,
                        ) {
                            Text(
                                text = kmpStringResource(res = MR.strings.retake_photo),
                                style = typography.textButton,
                                color = colors.white,
                            )
                        }
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(contentAlignment = Alignment.Center) {
                        AsyncImage(
                            model = viewState.photo,
                            fallback = painterResource(R.drawable.avatar_placeholder),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .border(
                                    width = Grid.d0_125,
                                    brush = Brush.verticalGradient(listOf(colors.white20, colors.white15)),
                                    shape = MDevTheme.shapes.medium,
                                )
                                .clip(MDevTheme.shapes.medium),
                        )

                        if (viewState.isPhotoLoading) {
                            CircularProgressIndicator()
                        }
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ButtonPrimary(
                        onClick = actions::onCreateNewAvatar,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Grid.d8, start = Grid.d5, end = Grid.d5),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = kmpStringResource(res = MR.strings.create_new_avatar),
                                style = typography.button,
                                color = colors.white,
                            )

                        }
                    }
                }
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = kmpStringResource(res = MR.strings.gallery_title).uppercase(),
                        style = typography.caption.copy(brush = Brush.verticalGradient(colors.heatGradient)),
                        modifier = Modifier.padding(start = Grid.d1, bottom = Grid.d4, top = Grid.d12),
                    )
                }
                items(viewState.avatars) { avatar ->
                    AvatarItem(
                        avatar = avatar,
                        onClick = {
                            actions.onAvatar(avatar)
                        },
                    )
                }
            }
        }
    }
}


@Composable
fun AvatarItem(avatar: Avatar, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .border(
                width = Grid.d0_125,
                brush = Brush.verticalGradient(listOf(MDevTheme.colors.white20, Color(0x26FFFFFF))),
                shape = MDevTheme.shapes.medium,
            )
            .clip(MDevTheme.shapes.medium)
            .clickable(onClick = onClick),
    ) {
        AsyncImage(
            model = if (avatar.status==AvatarStatus.Done) avatar.preview else avatar.style.preview,
            contentDescription = null,
            modifier = Modifier,
            contentScale = ContentScale.FillWidth,
        )

        if (avatar.status!=AvatarStatus.Done) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MDevTheme.colors.black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center,
            ) {
                val text = when (avatar.status) {
                    AvatarStatus.InProgress -> MR.strings.in_progress_title
                    else -> MR.strings.failed_title
                }
                Text(
                    text = kmpStringResource(res = text),
                    style = MDevTheme.typography.textSmallMedium,
                    color = MDevTheme.colors.white,
                )
            }
        }
    }
}


@Preview
@Composable
private fun FirstScreenPreview() {
    val actions = object : FirstScreen.Actions {
        override fun onCreateNewAvatar() = Unit
        override fun onAvatar(avatar: Avatar) = Unit
        override fun onCameraPermissionDenied() = Unit
        override fun onRetry() = Unit
        override fun onRetakePhoto(imageName: String, imageData: ByteArray) = Unit
        override fun loadAvatars() = Unit
    }
    MyApplicationTheme {
        Surface {
            Content(
                viewState = FirstViewState(),
                actions = actions,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
