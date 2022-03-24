@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package pab.lop.illustrashopandroid.ui.view.login_register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.ui.view_model.LoginRegisterViewModel
import pab.lop.illustrashopandroid.utils.URL_HEAD_LOCAL

@Preview
@Composable
fun Login(navController: NavHostController, loginRegisterViewModel: LoginRegisterViewModel) {

    val painter =
        rememberAsyncImagePainter(URL_HEAD_LOCAL)

  Column (
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
  ){
      //Text("Hola")
      val painter1 = rememberAsyncImagePainter("https://example.com/image.jpg")

      Button(
          onClick =  {
              loginRegisterViewModel.getAllUsers(){
                  Logger.i(loginRegisterViewModel.allUsersClientResponse.toString())
              }
              loginRegisterViewModel.getImage("MonaLisa") {
                  painter1.imageLoader.newBuilder(loginRegisterViewModel.)
              }
          }
      ){

      }



      Image(
          painter = painter,
          contentDescription = "Forest Image",
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
      )


/*
      AsyncImage(
          model = "${URL_HEAD_NO_API[0]}res/MonaLisa.jpg",
          contentDescription = null
      )*/



  }
}

/*
@Composable
fun MyContent(){

    // Declare a string that contains a url
    val mUrl = URL_HEAD_LOCAL

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
}*/
