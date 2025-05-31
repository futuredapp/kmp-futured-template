package app.futured.mdevcamp.android.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.android.R
import app.futured.kmptemplate.android.ui.components.Showcase
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.asPainterResource
import app.futured.mdevcamp.android.ui.theme.Grid
import app.futured.mdevcamp.android.ui.theme.MDevTheme

@Composable
fun CircleButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    val bg = ImageBitmap.imageResource(id = R.drawable.ic_button_bg)
    Surface(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier
            .size(Grid.d18)
            .drawBehind {
                // TODO can it be done better?
                val offsetX = -22.dp.toPx()
                val offsetY = -20.dp.toPx()
                withTransform(
                    {
                        translate(left = offsetX, top = offsetY)
                    },
                ) {
                    drawImage(
                        image = bg,
                        srcOffset = IntOffset.Zero,
                        dstSize = IntSize(120.dp.roundToPx(), 111.dp.roundToPx()),
                    )
                }
            },
        color = MDevTheme.colors.black,
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MDevTheme.colors.white,
                    modifier = Modifier.size(Grid.d6),
                    strokeWidth = Grid.d0_5,
                )
            } else {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(Grid.d6),
                    tint = MDevTheme.colors.white,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CircleButtonPreview() = Showcase {
    Column(
        modifier = Modifier
            .background(MDevTheme.colors.white10)
            .padding(Grid.d8),
        verticalArrangement = Arrangement.spacedBy(Grid.d4),
    ) {
        CircleButton(icon = MR.images.ic_arrow.asPainterResource(), onClick = {})
        CircleButton(icon = MR.images.ic_arrow.asPainterResource(), onClick = {}, isLoading = true)
    }
}
