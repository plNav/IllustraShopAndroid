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


var localSpacingCompact  = compositionLocalOf { SpacingCompact() }
var localSpacingMedium  = compositionLocalOf { SpacingMedium() }
var localSpacingExtended  = compositionLocalOf { SpacingExtended() }


val MaterialTheme.spacing_compact: SpacingCompact
    @Composable
    @ReadOnlyComposable
    get() = localSpacingCompact.current

val MaterialTheme.spacing_medium: SpacingMedium
    @Composable
    @ReadOnlyComposable
    get() = localSpacingMedium.current

val MaterialTheme.spacing_extended: SpacingExtended
    @Composable
    @ReadOnlyComposable
    get() = localSpacingExtended.current


data class SpacingCompact (
    val default : Dp = 0.dp,
    val extraSmall : Dp = 4.dp,
    val small : Dp = 8.dp,
    val mediumSmall : Dp = 16.dp,
    val mediumMedium : Dp = 22.dp,
    val mediumLarge : Dp = 28.dp,
    val large : Dp = 34.dp,
    val superLarge : Dp = 50.dp,
    val extraLarge : Dp = 60.dp
)

data class SpacingMedium (
    val default : Dp = 0.dp,
    val extraSmall : Dp = 10.dp,
    val small : Dp = 15.dp,
    val mediumSmall : Dp = 25.dp,
    val mediumMedium : Dp = 35.dp,
    val mediumLarge : Dp = 45.dp,
    val large : Dp = 55.dp,
    val superLarge : Dp = 70.dp,
    val extraLarge : Dp = 80.dp
)

data class SpacingExtended (
    val default : Dp = 0.dp,
    val extraSmall : Dp = 10.dp,
    val small : Dp = 20.dp,
    val mediumSmall : Dp = 30.dp,
    val mediumMedium : Dp = 50.dp,
    val mediumLarge : Dp = 70.dp,
    val large : Dp = 85.dp,
    val superLarge : Dp = 100.dp,
    val extraLarge : Dp = 120.dp
)


