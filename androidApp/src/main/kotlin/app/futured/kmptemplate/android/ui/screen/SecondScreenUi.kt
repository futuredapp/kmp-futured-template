@file:OptIn(ExperimentalMaterial3Api::class)

package app.futured.kmptemplate.android.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.android.tools.extensions.conditional
import app.futured.kmptemplate.android.ui.components.dialog.MDevDialog
import app.futured.kmptemplate.feature.model.ui.AvatarStyle
import app.futured.kmptemplate.feature.ui.secondScreen.SecondScreen
import app.futured.kmptemplate.feature.ui.secondScreen.SecondViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.asPainterResource
import app.futured.kmptemplate.resources.kmpStringResource
import app.futured.mdevcamp.android.ui.components.layout.StateLayout
import app.futured.mdevcamp.android.ui.components.layout.StateLayoutDefaults
import app.futured.mdevcamp.android.ui.components.layout.VerticalSpacer
import app.futured.mdevcamp.android.ui.theme.Grid
import app.futured.mdevcamp.android.ui.theme.MDevTheme
import coil.compose.AsyncImage

@Composable
fun SecondScreenUi(
    screen: SecondScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsState()


    Content(actions = actions, modifier = modifier, viewState = viewState)

    viewState.uiDialog?.let { dialog ->
        MDevDialog(uiDialog = dialog)
    }
}

@Composable
private fun Content(
    viewState: SecondViewState,
    actions: SecondScreen.Actions,
    modifier: Modifier = Modifier,
) {

    val colors = MDevTheme.colors
    val typography = MDevTheme.typography

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.background)
            .systemBarsPadding(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Grid.d2, start = Grid.d2, end = Grid.d2),
        ) {
            TextButton(
                onClick = actions::onCancel,
                modifier = Modifier,
            ) {
                Text(
                    text = kmpStringResource(res = MR.strings.cancel),
                    style = typography.textButton,
                    color = colors.white,
                )
            }

            when {
                viewState.isGenerating -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(end = Grid.d2)
                            .size(Grid.d5),
                        strokeWidth = Grid.d0_5,
                        color = colors.white,
                    )
                }

                viewState.selectedStyle != null -> {
                    TextButton(
                        onClick = actions::onGenerate,
                        modifier = Modifier,
                    ) {
                        Text(
                            text = kmpStringResource(res = MR.strings.generate_title),
                            style = typography.textButton,
                            color = colors.white,
                        )
                    }
                }

                else -> {}
            }
        }

        StateLayout(
            isLoading = viewState.isLoading,
            error = viewState.error,
            errorView = {
                StateLayoutDefaults.Error(uiError = it, retryButton = kmpStringResource(res = MR.strings.try_again)) {
                    actions.onRetry()
                }
            },
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                VerticalSpacer(size = Grid.d8)

                Text(
                    text = kmpStringResource(res = MR.strings.pick_your_style),
                    style = typography.title2,
                    color = colors.white,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                VerticalSpacer(size = Grid.d8)

                Box(modifier = Modifier.weight(1f).align(Alignment.CenterHorizontally).padding(horizontal = Grid.d5)) {
                    Box(
                        modifier = Modifier.align(Alignment.Center),
                    ) {
                        AsyncImage(
                            model = viewState.selectedStyle?.preview,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .padding(top = Grid.d3)
                                .border(
                                    width = Grid.d0_125,
                                    brush = Brush.verticalGradient(listOf(colors.white20, colors.white15)),
                                    shape = MDevTheme.shapes.medium,
                                )
                                .clip(MDevTheme.shapes.medium),
                        )

                        Text(
                            text = kmpStringResource(res = MR.strings.preview_title),
                            style = typography.textSmallBold,
                            color = colors.white,
                            modifier = Modifier
                                .background(colors.preview, MDevTheme.shapes.halfRoundedCorner)
                                .padding(vertical = Grid.d1_5, horizontal = Grid.d3)
                                .align(Alignment.TopCenter),
                        )
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Grid.d3),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(start = Grid.d5, end = Grid.d5, top = Grid.d10, bottom = Grid.d5),
                ) {
                    items(viewState.styles) { style ->
                        StyleItem(
                            avatarStyle = style,
                            isSelected = viewState.selectedStyle == style,
                            onClick = {
                                actions.onStyle(style)
                            },
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun StyleItem(avatarStyle: AvatarStyle, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val colors = MDevTheme.colors
    val heatGradient = colors.heatGradient
    Surface(
        modifier = modifier.width(Grid.d19),
        onClick = onClick,
        color = colors.card,
        shape = MDevTheme.shapes.medium,
        border = BorderStroke(
            width = Grid.d0_125,
            brush = Brush.verticalGradient(listOf(colors.white20, colors.white15)),
        ),
    ) {
        Column(
            modifier = Modifier,
        ) {
            Box(modifier = Modifier.height(Grid.d19)) {
                AsyncImage(
                    model = avatarStyle.preview,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
            }

            Text(
                text = avatarStyle.name,
                style = MDevTheme.typography.textSmallMedium,
                color = colors.white,
                maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .conditional(isSelected) {
                        background(Brush.verticalGradient(heatGradient))
                    }
                    .padding(vertical = Grid.d1_5, horizontal = Grid.d1),
            )
        }
    }
}

