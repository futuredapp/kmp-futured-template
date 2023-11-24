package app.futured.kmptemplate.resources

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.StringDesc

/**
 * Load a string resource.
 *
 * @param res shared multiplatform resource identifier
 * @return the string data associated with the resource
 */
@Composable
@ReadOnlyComposable
fun kmpStringResource(res: StringResource): String {
    return stringResource(id = res.resourceId)
}

/**
 * Load a string resource with formatting.
 *
 * @param res shared multiplatform resource identifier
 * @param formatArgs the format arguments
 * @return the string data associated with the resource
 */
@Composable
@ReadOnlyComposable
fun kmpStringResource(res: StringResource, vararg formatArgs: Any): String {
    return stringResource(id = res.resourceId, *formatArgs)
}

/**
 * Convert provided [StringDesc] from KMP to [String] using Android [Context].
 *
 * @return the string data associated with the resource.
 */
@Composable
fun StringDesc.localized() = toString(LocalContext.current)
