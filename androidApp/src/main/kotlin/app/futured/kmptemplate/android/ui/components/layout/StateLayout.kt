package app.futured.mdevcamp.android.ui.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.android.ui.components.Showcase
import app.futured.kmptemplate.feature.tool.UiError
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource
import app.futured.kmptemplate.resources.localized
import app.futured.mdevcamp.android.ui.components.button.ButtonPrimary
import app.futured.mdevcamp.android.ui.theme.MDevTheme
import app.futured.mdevcamp.android.ui.theme.Grid
import dev.icerock.moko.resources.desc.desc

/**
 * Default views used in [StateLayout].
 */
object StateLayoutDefaults {

    @Composable
    fun Loading(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }

    @Composable
    fun Empty(
        modifier: Modifier = Modifier,
        title: String = kmpStringResource(res = MR.strings.empty_title),
        description: String = kmpStringResource(res = MR.strings.empty_description),
        buttonText: String = kmpStringResource(res = MR.strings.try_again),
        reloadAction: (() -> Unit)? = null,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = MDevTheme.dimensions.contentPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = MDevTheme.typography.title2,
                color = MDevTheme.colors.white,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            VerticalSpacer(Grid.d4)

            Text(
                text = description,
                style = MDevTheme.typography.textNormalRegular,
                color = MDevTheme.colors.white60,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            reloadAction?.let {
                VerticalSpacer(Grid.d8)

                ButtonPrimary(
                    text = buttonText,
                    onClick = reloadAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Grid.d5),
                )
            }
        }
    }

    @Composable
    fun Error(
        uiError: UiError,
        modifier: Modifier = Modifier,
        retryButton: String? = null,
        onRetryButton: () -> Unit = {},
    ) {
        Box(modifier = modifier) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Grid.d10),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = uiError.title.localized(),
                    style = MDevTheme.typography.title2,
                    color = MDevTheme.colors.white,
                    textAlign = TextAlign.Center,
                )
                uiError.message?.let {
                    VerticalSpacer(size = Grid.d6)
                    Text(
                        text = it.localized(),
                        style = MDevTheme.typography.textNormalRegular,
                        color = MDevTheme.colors.white60,
                        textAlign = TextAlign.Center,
                    )
                }
                retryButton?.let {
                    VerticalSpacer(size = Grid.d8)
                    ButtonPrimary(
                        text = it.uppercase(),
                        onClick = onRetryButton,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Grid.d5),
                    )
                }
            }
        }
    }

    @Composable
    fun ErrorWithRetryButton(
        uiError: UiError,
        onRetryClick: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Error(
            uiError = uiError,
            modifier = modifier,
            retryButton = kmpStringResource(MR.strings.try_again),
            onRetryButton = onRetryClick,
        )
    }
}

/**
 * Layout which displays one of 3 states:
 * - loading
 * - error
 * - content
 * - empty
 *
 * By default, StateLayout uses loading and error views from [StateLayoutDefaults] and these
 * can be overridden. There is also [StateLayoutDefaults.ErrorWithRetryButton] view which provides
 * error state with "Retry" button out of the box.
 *
 * Default views are set up to play well with Material3 theme's background, onBackground and primary colors
 * and can be used out of the box when theme is used properly (eg. in yellow / magenta onboarding).
 */
@Composable
fun StateLayout(
    isLoading: Boolean,
    error: UiError?,
    modifier: Modifier = Modifier,
    isEmpty: Boolean = false,
    loadingView: @Composable () -> Unit = { StateLayoutDefaults.Loading(modifier = Modifier.fillMaxSize()) },
    errorView: @Composable (error: UiError) -> Unit = { StateLayoutDefaults.Error(uiError = it, modifier.fillMaxSize()) },
    emptyView: @Composable () -> Unit = { StateLayoutDefaults.Empty(modifier.fillMaxSize()) },
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier) {
        when {
            isLoading -> loadingView()
            error != null -> errorView(error)
            isEmpty -> emptyView()
            else -> content()
        }
    }
}

@Preview
@Composable
private fun StateLayoutPreviewLoading() {
    Showcase {
        StateLayout(
            isLoading = true,
            error = null,
            isEmpty = false,
            modifier = Modifier.size(400.dp),
        ) {
            Text(
                text = "I'm a content",
                modifier = Modifier.align(Alignment.Center),
                style = MDevTheme.typography.textNormalRegular,
                color = MDevTheme.colors.white,
            )
        }
    }
}

@Preview
@Composable
private fun StateLayoutPreviewError() {
    Showcase {
        StateLayout(
            isLoading = false,
            error = UiError(
                title = "Error Title".desc(),
                message = "Error message".desc(),
            ),
            isEmpty = false,
            modifier = Modifier.size(400.dp),
            errorView = {
                StateLayoutDefaults.ErrorWithRetryButton(uiError = it, onRetryClick = { })
            },
        ) {
            Text(
                text = "I'm a content",
                modifier = Modifier.align(Alignment.Center),
                style = MDevTheme.typography.textNormalRegular,
                color = MDevTheme.colors.white,
            )
        }
    }
}

@Preview
@Composable
private fun StateLayoutPreviewContent() {
    Showcase {
        StateLayout(
            isLoading = false,
            error = null,
            isEmpty = false,
            modifier = Modifier.size(400.dp),
        ) {
            Text(
                text = "I'm a content",
                modifier = Modifier.align(Alignment.Center),
                style = MDevTheme.typography.textNormalRegular,
                color = MDevTheme.colors.white,
            )
        }
    }
}

@Preview
@Composable
private fun StateLayoutPreviewEmpty() = Showcase {
    StateLayout(
        isLoading = false,
        error = null,
        isEmpty = true,
        modifier = Modifier.size(400.dp),
    ) {
        Text(
            text = "I'm a content",
            modifier = Modifier.align(Alignment.Center),
            style = MDevTheme.typography.textNormalRegular,
            color = MDevTheme.colors.white,
        )
    }
}
