package pab.lop.illustrashopandroid.ui.view.login_register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.ui.view_model.LoginRegisterViewModel


@Composable
fun Login(navController: NavHostController, loginRegisterViewModel: LoginRegisterViewModel) {

  Column (
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
  ){
      Text("Hola")

      Button(
          onClick =  {
              loginRegisterViewModel.getAllUsers(){
                Logger.i(loginRegisterViewModel.allUsersClientResponse.toString())
      }
          }
      ){

      }
  }
}