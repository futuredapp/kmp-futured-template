package app.futured.kmptemplate.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import dev.icerock.moko.resources.ColorResource

/**
 * Convert provided [ColorResource] from KMP to Compose [Color].
 *
 * @return the [Color] associated with resource.
 */
@Composable
fun ColorResource.asComposeColorResource(): Color = colorResource(id = this.resourceId)
