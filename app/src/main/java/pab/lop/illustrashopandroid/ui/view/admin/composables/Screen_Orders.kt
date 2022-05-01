package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.utils.allOrders

@Composable
fun Orders(
    navController: NavController,
    adminViewModel: AdminViewModel,
    context: Context,
    customSpacing: Spacing
) {
    Column(){
        for(item in allOrders){
            Text(text = item.total.toString())
        }
    }

}