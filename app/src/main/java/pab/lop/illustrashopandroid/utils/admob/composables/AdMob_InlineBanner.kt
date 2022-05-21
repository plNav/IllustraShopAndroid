package pab.lop.illustrashopandroid.utils.admob.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import pab.lop.illustrashopandroid.utils.TEST_BANNER

@Composable
fun InlineBanner() {
    val adWidth = LocalConfiguration.current.screenWidthDp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 1.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(
                        context, adWidth
                    )
                    adUnitId = TEST_BANNER
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}