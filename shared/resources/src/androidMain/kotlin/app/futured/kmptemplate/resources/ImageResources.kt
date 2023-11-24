package app.futured.kmptemplate.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import dev.icerock.moko.resources.ImageResource

/**
 * Convert provided [ImageResource] from KMP to Compose [Painter].
 *
 * @return the [Painter] associated with resource.
 */
@Composable
fun ImageResource.asPainterResource(): Painter = painterResource(id = this.drawableResId)

/**
 * Load a painter resource.
 *
 * @param res shared multiplatform resource identifier
 * @return Shared image resource as Compose painter resource
 */
@Composable
fun kmpPainterResource(res: ImageResource): Painter {
    return painterResource(id = res.drawableResId)
}
