package pab.lop.illustrashopandroid.ui.view.pay.composables

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.pay.PayViewModel
import pab.lop.illustrashopandroid.utils.admob.composables.AdaptiveBanner
import pab.lop.illustrashopandroid.utils.admob.composables.InlineBanner
import pab.lop.illustrashopandroid.utils.admob.composables.InterstitialButton
import pab.lop.illustrashopandroid.utils.admob.composables.RegularBanner

@Composable
fun Pay(
    payViewModel: PayViewModel,
    navController: NavController,
    contexttt: Context,
    customSpacing: Spacing
){
    Text("HelloWorld")

    val adWidth = LocalConfiguration.current.screenWidthDp - 32
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ){

        AdaptiveBanner()

        InlineBanner()

        RegularBanner()


        InterstitialButton(navController)

        Text("More text")

    }

}