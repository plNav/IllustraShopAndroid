package pab.lop.illustrashopandroid.ui.view.pay.composables

import android.content.Context
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.pay.PayViewModel

@Composable
fun Pay(
    payViewModel: PayViewModel,
    navController: NavController,
    context: Context,
    customSpacing: Spacing
){
    Text("HelloWorld")

}