package pab.lop.illustrashopandroid.utils.admob.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import pab.lop.illustrashopandroid.utils.TEST_BANNER

@Composable
fun RegularBanner(){

    Column(){
        Text(
            text = "Regular Banner",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        AndroidView(
            factory = { context ->
                AdView(context).apply{
                    adSize = AdSize.BANNER
                    adUnitId = TEST_BANNER
                    loadAd(AdRequest.Builder().build())
                }
            }
        )

    }
}
