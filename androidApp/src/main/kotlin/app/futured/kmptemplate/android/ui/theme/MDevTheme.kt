@file:SuppressLint("ComposeCompositionLocalUsage")

package app.futured.mdevcamp.android.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val mDevTypography = mDevTypography
    val mDevColors = mDevColors
    val mDevShapes = mDevShapes
    val mDevDimensions = mDevDimensions

    CompositionLocalProvider(
        LocalMDevTypography provides mDevTypography,
        LocalMDevShapes provides mDevShapes,
        LocalMDevDimensions provides mDevDimensions,
        LocalMDevColors provides mDevColors,
    ) {
        MaterialTheme(
            colorScheme = materialThemeColors,
            typography = defaultTypography,
            shapes = materialShapes,
            content = content,
        )
    }
}

object MDevTheme {
    val typography: MDevTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalMDevTypography.current

    val dimensions: MDevDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalMDevDimensions.current

    val colors: MDevColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMDevColors.current

    val shapes: MDevShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalMDevShapes.current
}

internal val LocalMDevTypography =
    staticCompositionLocalOf<MDevTypography> { error("MDevTypography not provided") }

internal val LocalMDevColors =
    staticCompositionLocalOf<MDevColors> { error("MDevColors not provided") }

internal val LocalMDevDimensions =
    staticCompositionLocalOf<MDevDimensions> { error("MDevDimensions not provided") }

internal val LocalMDevShapes =
    staticCompositionLocalOf<MDevShapes> { error("MDevShapes not provided") }
