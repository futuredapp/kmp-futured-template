@file:Suppress("MagicNumber")

package app.futured.mdevcamp.android.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

private val White: Color = Color(0xFFFFFFFF)
private val Background: Color = Color(0xFF080808)
private val Black: Color = Color(0xFF000000)
private val White80: Color = Color(0xCCFFFFFF)
private val White70: Color = Color(0xB2FFFFFF)
private val White60: Color = Color(0x99FFFFFF)
private val White50: Color = Color(0x80FFFFFF)
private val White40: Color = Color(0x66FFFFFF)
private val White20: Color = Color(0x33FFFFFF)
private val White15: Color = Color(0x26FFFFFF)
private val Orange: Color = Color(0xFFFF5F00)
private val Pink: Color = Color(0xFFFF0099)
private val White10: Color = Color(0x1AFFFFFF)
private val Gray: Color = Color(0xFF9C9C9C)
private val Surface: Color = Color(0xFF121212)
private val Card: Color = Color(0xFF272727)
private val Preview: Color = Color(0xFF383838)

val badgeGradientColors = listOf(Color(0xFF222222), Color(0xFF101010))

val changeMyMindTextGradientColors = listOf(Color(0xFF707070), Color(0xFF484848))

class MDevColors(
    val white: Color = White,
    val white80: Color = White80,
    val white70: Color = White70,
    val white60: Color = White60,
    val white50: Color = White50,
    val white40: Color = White40,
    val white20: Color = White20,
    val white15: Color = White15,
    val white10: Color = White10,
    val background: Color = Background,
    val black: Color = Black,
    val orange: Color = Orange,
    val pink: Color = Pink,
    val gray: Color = Gray,
    val surface: Color = Surface,
    val heatGradient: List<Color> = listOf(Orange, Pink),
    val card: Color = Card,
    val preview: Color = Preview,
)

internal val mDevColors = MDevColors()

val materialThemeColors = darkColorScheme(
    primary = Orange,
    primaryContainer = Orange,
    secondary = Pink,
    secondaryContainer = Pink,
    background = Background,
    surface = Surface,
    error = Pink,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White,
    onError = White,
)
