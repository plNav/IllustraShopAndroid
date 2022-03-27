package pab.lop.illustrashopandroid.ui.view.login_register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pab.lop.illustrashopandroid.ui.view_model.LoginRegisterViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.IllustraShopAndroidTheme
import pab.lop.illustrashopandroid.ui.theme.spacing_compact
import pab.lop.illustrashopandroid.ui.theme.spacing_extended
import pab.lop.illustrashopandroid.ui.theme.spacing_medium
import pablo_lonav.android.utils.ScreenNav


@Composable
fun Login(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel,
    context: Context
) {

    IllustraShopAndroidTheme {

        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val emptyError = stringResource(R.string.emptyError)

        var passwordVisibility by remember { mutableStateOf(false) }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp)

        ) {

            Spacer(modifier = Modifier.height(MaterialTheme.spacing_compact.small))
            Logger.wtf("${MaterialTheme.spacing_compact.mediumMedium} ${MaterialTheme.spacing_medium.mediumMedium} ${MaterialTheme.spacing_extended.mediumMedium}")

            Text(
                text = stringResource(R.string.login),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = email.value,
                onValueChange = { it.also { email.value = it } },
                singleLine = true,
                label = {
                    Text(
                        text = stringResource(R.string.email),
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary
                        )
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.email),
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary
                        )
                    )
                },
                trailingIcon = {
                    val image = Icons.Filled.Email
                    Icon(
                        imageVector = image,
                        stringResource(R.string.email)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedLabelColor = MaterialTheme.colors.primary,
                    unfocusedLabelColor = MaterialTheme.colors.secondary,
                    cursorColor = MaterialTheme.colors.primary
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { it.also { password.value = it } },
                singleLine = true,
                label = {
                    Text(
                        text = stringResource(R.string.Password),
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary
                        )
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.Password),
                        style = TextStyle(
                            color = MaterialTheme.colors.secondary
                        ),
                    )
                },
                visualTransformation =
                    if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image =
                        if (passwordVisibility) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility }
                    ) {
                        Icon(
                            imageVector = image,
                            stringResource(R.string.Password)
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.secondary,
                    focusedLabelColor = MaterialTheme.colors.primary,
                    unfocusedLabelColor = MaterialTheme.colors.secondary,
                    cursorColor = MaterialTheme.colors.primary
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    if (false/*email.value.isEmpty() || password.value.isEmpty()*/)
                        Toast.makeText(context, emptyError, Toast.LENGTH_SHORT).show()
                    else {
                        navController.navigate(ScreenNav.ValidateScreen.route)
                        //backLogin = true
                        //loginRegisterViewModel.validateClient(email = email.value, passw = password.value)
                        //navController.navigate(ScreenNav.ValidateLoginScreen.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            ) {
                Text(
                    text = stringResource(R.string.validate),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    // navController.navigate(ScreenNav.RegisterScreen.route)
                          navController.navigate(ScreenNav.MainScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}


/*


@Composable
fun Login2(navController: NavHostController, loginRegisterViewModel: LoginRegisterViewModel) {

    val painter =
        rememberAsyncImagePainter(URL_HEAD_LOCAL)

  Column (
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
  ){
      val painter = rememberAsyncImagePainter("http://192.168.1.93:8082/api/images/American_Gothic")

      Button(
          onClick =  {
              loginRegisterViewModel.getAllUsers(){
                  Logger.i(loginRegisterViewModel.allUsersClientResponse.toString())
              }
           */
/*   loginRegisterViewModel.getImage("MonaLisa") {
                  painter1.imageLoader.newBuilder(loginRegisterViewModel.)
              }*//*

          }
      ){

      }



      Image(
          painter = painter,
          contentDescription = "Forest Image",
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
      )


      AsyncImage(
          modifier = Modifier.fillMaxSize(),
          model = painter,
          painter = painter,
          contentDescription = null,
          contentScale = ContentScale.Crop
      )
     // MyContent()



  }
}
*/

/*
@Composable
fun MyContent(){

    // Declare a string that contains a url
    val mUrl = "http://192.168.1.93:8082/api/images/American_Gothic"

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(mUrl)
        }
    }, update = {
        it.loadUrl(mUrl)
    })
}
*/
