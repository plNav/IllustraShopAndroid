package pab.lop.illustrashopandroid.utils.admob

import android.content.Context
import androidx.navigation.NavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.utils.TEST_INTERSTITIAL
import pab.lop.illustrashopandroid.utils.ScreenNav

var interstitialAd : InterstitialAd? = null


fun loadInterstitial(context: Context){
    InterstitialAd.load(
        context,
        TEST_INTERSTITIAL,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(interstitial: InterstitialAd) {
                interstitialAd = interstitial
                Logger.d("Interstitial Ad Loaded")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                interstitialAd = null
                Logger.w("Failed to load interstitial \n ${loadAdError.message}")
            }

        }
    )
}

fun addInterstitialCallbacks(context: Context, navController: NavController){
    interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
                Logger.d("Interstitial add dismissed")
            navController.navigate(ScreenNav.MainScreen.route)
            }

        override fun onAdFailedToShowFullScreenContent(showError: AdError) {
            Logger.w("Failed to show interstitial ad \n ${showError.message}")
        }

        override fun onAdShowedFullScreenContent() {
            interstitialAd = null
            loadInterstitial(context)
            Logger.d("Interstitial Ad showed full Screen")
        }
    }
}

fun showInterstitial(context : Context){
    val activity = context.findActivity()

    if(interstitialAd != null){
        interstitialAd?.show(activity!!)

    }else{
        Logger.w("Interstitial add Not Ready")
    }
}