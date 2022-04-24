package pab.lop.illustrashopandroid.utils.admob.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.utils.admob.addInterstitialCallbacks
import pab.lop.illustrashopandroid.utils.admob.loadInterstitial
import pab.lop.illustrashopandroid.utils.admob.showInterstitial

@Composable
fun InterstitialButton(navController: NavController) {
    val context = LocalContext.current

  Column(){
      Button(
          onClick = {
              loadInterstitial(context)
              addInterstitialCallbacks(context, navController)
              showInterstitial(context)

          },
          modifier = Modifier.padding(16.dp)
      ){
          Text(text = "Show Interstitial")
      }

      /*Column(
          modifier = Modifier
              .weight(1f),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
      ) {
       //   Text("Hello ad network")
      }*/
  }

}