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

)

private val LightColorPalette = lightColors(
    primary = GreenPrimary,
    primaryVariant = GreenPrimaryVariant,
    secondary = SecondaryGreen,
    onError =  GrayDisabled

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)
//TODO IMPLEMENTAR NO ACTION BAR
@Composable
fun IllustraShopAndroidTheme (
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

 /*   val windowInfo = rememberWindowInfo()

    when (windowInfo.screenWidthInfo) {
        is WindowInfo.WindowType.Compat -> {

            val localSpacing = localSpacingCompact
            val localData = SpacingCompact(
                default  = 0.dp,
                extraSmall  = 4.dp,
                small  = 8.dp,
                mediumSmall = 16.dp,
                mediumMedium = 22.dp,
                mediumLarge = 28.dp,
                large  = 34.dp,
                extraLarge  = 60.dp
            )
            CompositionLocalProvider(localSpacing provides localData) {
                MaterialTheme(
                    colors = colors,
                    typography = Typography,
                    shapes = Shapes,
                    content = content
                )
            }
        }
        is WindowInfo.WindowType.Medium -> {
            val localSpacing = localSpacingMedium
            val localData = SpacingMedium()
            CompositionLocalProvider(localSpacing provides localData) {
                MaterialTheme(
                    colors = colors,
                    typography = Typography,
                    shapes = Shapes,
                    content = content
                )
            }
        }
        else -> {
            val localSpacing = localSpacingExtended
            val localData = SpacingExtended()*/
          //  CompositionLocalProvider(localSpacing provides localData) {
                MaterialTheme(
                    colors = colors,
                    typography = Typography,
                    shapes = Shapes,
                    content = content
                )
           // }
        }
 /*   }
}
*/

