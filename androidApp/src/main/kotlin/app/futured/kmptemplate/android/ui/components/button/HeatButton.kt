package app.futured.mdevcamp.android.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import app.futured.kmptemplate.android.ui.components.Showcase
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.asPainterResource
import app.futured.mdevcamp.android.ui.theme.Grid
import app.futured.mdevcamp.android.ui.theme.MDevTheme

@Composable
fun HeatButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .padding(horizontal = Grid.d1)
            .size(Grid.d18),
        onClick = onClick,
        color = MDevTheme.colors.surface,
        shape = CircleShape,
        border = BorderStroke(
            Dp.Hairline,
            Brush.verticalGradient(
                listOf(Color(0x30FFFFFF), Color(0x0AFFFFFF)),
            ),
        ),
    ) {
        Box(
            modifier = Modifier
                .padding(Grid.d1)
                .border(
                    BorderStroke(
                        Grid.d0_25,
                        brush = Brush.verticalGradient(MDevTheme.colors.heatGradient),
                    ),
                    shape = CircleShape,
                ),
        ) {
            Icon(
                painter = MR.images.ic_qr_code.asPainterResource(),
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(
                                Brush.verticalGradient(listOf(Color(0xFFE4E4E4), Color(0xFFABABAB))),
                                blendMode = BlendMode.SrcAtop,
                            )
                        }
                    }
                    .padding(Grid.d4),
            )
        }
    }
}

@Preview
@Composable
private fun HeatButtonPreview() = Showcase {
    HeatButton(onClick = {})
}
