package pab.lop.illustrashopandroid.utils.admob.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.utils.admob.addInterstitialCallbacks
import pab.lop.illustrashopandroid.utils.admob.loadInterstitial
import pab.lop.illustrashopandroid.utils.admob.showInterstitial
import pab.lop.illustrashopandroid.utils.userDefaultNoAuth
import pab.lop.illustrashopandroid.utils.userSelected

@Composable
fun InterstitialButton(navController: NavController) {

    val context = LocalContext.current

    Column {
        Text(
            text = stringResource(R.string.no_auth),
            color = MaterialTheme.colors.primary,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                userSelected = userDefaultNoAuth
                loadInterstitial(context)
                addInterstitialCallbacks(context, navController)
                showInterstitial(context)
            }
        )
    }
}