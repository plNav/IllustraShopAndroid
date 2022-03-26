package pab.lop.illustrashopandroid.ui.view.login_register

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.ui.theme.IllustraShopAndroidTheme
import pab.lop.illustrashopandroid.ui.view_model.LoginRegisterViewModel

@Composable
fun Validate(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel,
    context: Context
) {

    IllustraShopAndroidTheme {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Card(
                elevation = 20.dp,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.9f)
            ) {
//TODO COLUMN WITH INFO
                Text("Hello world", modifier = Modifier.fillMaxSize())





            }
        }
    }
}

