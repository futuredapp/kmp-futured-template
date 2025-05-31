@file:Suppress("MagicNumber")

package app.futured.mdevcamp.android.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes

val materialShapes = Shapes(
    small = RoundedCornerShape(Grid.d2),
    medium = RoundedCornerShape(Grid.d4),
    large = RoundedCornerShape(Grid.d5),
)

val mDevShapes = MDevShapes(
    small = RoundedCornerShape(Grid.d0_75),
    medium = RoundedCornerShape(Grid.d2),
    large = RoundedCornerShape(Grid.d3),
    extraLarge = RoundedCornerShape(Grid.d4),
    halfRoundedCorner = RoundedCornerShape(50),
)

data class MDevShapes(
    val small: CornerBasedShape,
    val medium: CornerBasedShape,
    val large: CornerBasedShape,
    val extraLarge: CornerBasedShape,
    val halfRoundedCorner: CornerBasedShape,
)
