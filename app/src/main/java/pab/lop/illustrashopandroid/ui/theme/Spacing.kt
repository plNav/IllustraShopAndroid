package pab.lop.illustrashopandroid.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pab.lop.illustrashopandroid.utils.WindowInfo
import pab.lop.illustrashopandroid.utils.rememberWindowInfo


sealed class Spacing(
    val default: Dp,
    val extraSmall: Dp,
    val small: Dp,
    val mediumSmall: Dp,
    val mediumMedium: Dp,
    val mediumLarge: Dp,
    val large: Dp,
    val superLarge: Dp,
    val extraLarge: Dp
) {
    object SpacingCompact : Spacing(
        default = 0.dp,
        extraSmall = 4.dp,
        small = 8.dp,
        mediumSmall = 16.dp,
        mediumMedium = 22.dp,
        mediumLarge = 28.dp,
        large = 34.dp,
        superLarge = 50.dp,
        extraLarge = 60.dp
    )

    object SpacingMedium : Spacing(
        default = 0.dp,
        extraSmall = 10.dp,
        small = 15.dp,
        mediumSmall = 25.dp,
        mediumMedium = 35.dp,
        mediumLarge = 45.dp,
        large = 55.dp,
        superLarge = 70.dp,
        extraLarge = 80.dp
    )

    object SpacingExtended : Spacing(
        default = 0.dp,
        extraSmall = 10.dp,
        small = 20.dp,
        mediumSmall = 30.dp,
        mediumMedium = 50.dp,
        mediumLarge = 70.dp,
        large = 85.dp,
        superLarge = 100.dp,
        extraLarge = 120.dp
    )

}


