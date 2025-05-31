@file:Suppress("MagicNumber")

package app.futured.mdevcamp.android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import app.futured.kmptemplate.android.R

private val dinProFontFamily = FontFamily(
    Font(R.font.dinpro_regular, FontWeight.W400),
    Font(R.font.dinpro_medium, FontWeight.W500),
    Font(R.font.dinpro_bold, FontWeight.W700),
)

private val azeretMonoFamily = FontFamily(
    Font(R.font.azeretmono_regular, FontWeight.W400),
    Font(R.font.azeretmono_medium, FontWeight.W500),
    Font(R.font.azeretmono_semibold, FontWeight.W600),
    Font(R.font.azeretmono_bold, FontWeight.W700),
)

private val bigTitle = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W700,
    fontSize = 36.sp,
    lineHeight = 36.sp,
    letterSpacing = (-1.2).sp,
)

private val title = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W500,
    fontSize = 32.sp,
    lineHeight = 36.8.sp,
)

private val title2 = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W700,
    fontSize = 28.sp,
    lineHeight = 33.6.sp,
)

private val title3 = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W500,
    fontSize = 24.sp,
    lineHeight = 36.sp,
)

private val title3Regular = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W400,
    fontSize = 24.sp,
    lineHeight = 32.4.sp,
)

private val title4 = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W500,
    fontSize = 22.sp,
    lineHeight = 30.8.sp,
)

private val textLargeRegular = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W400,
    fontSize = 18.sp,
    lineHeight = 27.sp,
)

private val textNormalMedium = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
    lineHeight = 20.8.sp,
)

private val textNormalRegular = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 20.8.sp,
)

private val textSmallRegular = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W400,
    fontSize = 13.sp,
    lineHeight = 19.5.sp,
)

private val textSmallMedium = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W500,
    fontSize = 13.sp,
    lineHeight = 19.5.sp,
)

private val textSmallBold = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W700,
    fontSize = 12.sp,
    lineHeight = 12.sp,
)

private val caption = TextStyle(
    fontFamily = azeretMonoFamily,
    fontWeight = FontWeight.W600,
    fontSize = 11.sp,
    lineHeight = 13.2.sp,
)

private val navigationBarText = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W500,
    fontSize = 12.sp,
    lineHeight = 15.46.sp,
)

private val textButton = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
    lineHeight = 16.sp,
)

private val button = TextStyle(
    fontFamily = dinProFontFamily,
    fontWeight = FontWeight.W500,
    fontSize = 15.sp,
    lineHeight = 16.sp,
)

data class MDevTypography(
    val bigTitle: TextStyle,
    val title: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val title3Regular: TextStyle,
    val title4: TextStyle,
    val textLargeRegular: TextStyle,
    val textNormalMedium: TextStyle,
    val textNormalRegular: TextStyle,
    val textSmallRegular: TextStyle,
    val textSmallMedium: TextStyle,
    val textSmallBold: TextStyle,
    val navigationBarText: TextStyle,
    val caption: TextStyle,
    val textButton: TextStyle,
    val button: TextStyle,
)

internal val mDevTypography = MDevTypography(
    bigTitle = bigTitle,
    title = title,
    title2 = title2,
    title3 = title3,
    title3Regular = title3Regular,
    title4 = title4,
    textLargeRegular = textLargeRegular,
    textNormalMedium = textNormalMedium,
    textNormalRegular = textNormalRegular,
    textSmallRegular = textSmallRegular,
    textSmallMedium = textSmallMedium,
    textSmallBold = textSmallBold,
    navigationBarText = navigationBarText,
    caption = caption,
    textButton = textButton,
    button = button,
)

internal val defaultTypography = Typography().let {
    it.copy(
        displayLarge = it.displayLarge.applyStrikethrough(),
        displayMedium = it.displayMedium.applyStrikethrough(),
        displaySmall = it.displaySmall.applyStrikethrough(),
        headlineLarge = it.headlineLarge.applyStrikethrough(),
        headlineMedium = it.headlineMedium.applyStrikethrough(),
        headlineSmall = it.headlineSmall.applyStrikethrough(),
        titleLarge = it.titleLarge.applyStrikethrough(),
        titleMedium = it.titleMedium.applyStrikethrough(),
        titleSmall = it.titleSmall.applyStrikethrough(),
        bodyLarge = it.bodyLarge.applyStrikethrough(),
        bodyMedium = it.bodyMedium.applyStrikethrough(),
        bodySmall = it.bodySmall.applyStrikethrough(),
        labelLarge = it.labelLarge.applyStrikethrough(),
        labelMedium = it.labelMedium.applyStrikethrough(),
        labelSmall = it.labelSmall.applyStrikethrough(),
    )
}

fun TextStyle.applyStrikethrough() = copy(textDecoration = TextDecoration.LineThrough)
