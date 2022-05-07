package pab.lop.illustrashopandroid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import pab.lop.illustrashopandroid.utils.WindowInfo
import pab.lop.illustrashopandroid.utils.rememberWindowInfo

private val DarkColorPalette = darkColors(
    primary = GreenPrimary,
    primaryVariant = GreenPrimaryVariant,
    secondary = SecondaryGreen,
    onError = GrayDisabled,
    onSecondary = OnSecondaryRed,
    surface = SurfaceAlmostBlack,
    background = SurfaceAlmostBlack

)

private val LightColorPalette = lightColors(
    primary = GreenPrimary,
    primaryVariant = GreenPrimaryVariant,
    secondary = SecondaryGreen,
    onError = GrayDisabled,
    onSecondary = OnSecondaryRed,
    surface = SurfaceAlmostWhite,
    background = SurfaceAlmostWhite,
)

@Composable
fun IllustraShopAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

