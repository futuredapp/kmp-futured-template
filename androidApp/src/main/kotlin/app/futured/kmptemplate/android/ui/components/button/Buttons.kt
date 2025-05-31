package app.futured.mdevcamp.android.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.android.R
import app.futured.kmptemplate.android.tools.compose.ComponentPreviews
import app.futured.kmptemplate.android.ui.components.Showcase
import app.futured.mdevcamp.android.ui.theme.Grid
import app.futured.mdevcamp.android.ui.theme.MDevTheme

@Composable
fun ButtonPrimary(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    ButtonInternal(onClick = onClick, modifier = modifier, isLoading = isLoading, enabled = enabled, content = content)
}

@Composable
fun ButtonPrimary(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
) {
    ButtonInternal(
        onClick = onClick,
        modifier = modifier,
        isLoading = isLoading,
        enabled = enabled,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = MDevTheme.colors.white,
                strokeWidth = Grid.d0_5,
                modifier = Modifier
                    .padding(Grid.d0_75)
                    .size(Grid.d4),
            )
        } else {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MDevTheme.typography.button,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun ButtonInternal(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val pinkBg = ImageBitmap.imageResource(id = R.drawable.button_primary_pink_bg)
    val orangeBg = ImageBitmap.imageResource(id = R.drawable.button_primary_orange_bg)

    Button(
        onClick = { if (isLoading.not()) onClick() },
        modifier = modifier.drawBehind {
            val width = size.width.times(1.1f).toInt()
            val height = size.height.times(1.1f).toInt()
            val diffX = (width - size.width) / 2
            val diffY = (height - size.height) / 2

            drawImage(
                image = pinkBg,
                srcOffset = IntOffset.Zero,
                dstSize = IntSize(width, height),
                dstOffset = IntOffset(-diffX.toInt() + Grid.d1.roundToPx(), -diffY.toInt() - Grid.d2.roundToPx()),
            )

            drawImage(
                image = orangeBg,
                srcOffset = IntOffset.Zero,
                dstSize = IntSize(width, height),
                dstOffset = IntOffset(-diffX.toInt() - Grid.d1.roundToPx(), diffY.toInt() + Grid.d1.roundToPx()),
            )
        },
        elevation = ButtonDefaults.buttonElevation(Grid.d0, Grid.d0, Grid.d0),
        enabled = enabled && isLoading.not(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MDevTheme.colors.black,
            disabledContainerColor = MDevTheme.colors.black,
            contentColor = MDevTheme.colors.white,
            disabledContentColor = MDevTheme.colors.white,
        ),
        contentPadding = PaddingValues(horizontal = Grid.d5, vertical = Grid.d5),
        shape = MDevTheme.shapes.halfRoundedCorner,
    ) {
        content()
    }
}

@ComponentPreviews
@Composable
private fun PrimaryButtonsPreview() = Showcase {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(Grid.d5),
    ) {
        ButtonPrimary(
            text = "Set as profile picture",
            isLoading = false,
            onClick = {},
            modifier = Modifier,
        )
        ButtonPrimary(
            text = "Create new avatar",
            isLoading = false,
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
        ButtonPrimary(
            text = "Disabled Set as profile picture",
            isLoading = false,
            onClick = {},
            enabled = false,
            modifier = Modifier,
        )
        ButtonPrimary(
            text = "Loading",
            isLoading = true,
            onClick = {},
            modifier = Modifier,
        )
    }
}
