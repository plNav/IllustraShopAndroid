package pab.lop.illustrashopandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.ui.theme.IllustraShopAndroidTheme
import pab.lop.illustrashopandroid.ui.view.login_register.Login
import pab.lop.illustrashopandroid.ui.view.login_register.Validate
import pab.lop.illustrashopandroid.ui.view.main.Main
import pab.lop.illustrashopandroid.ui.view_model.LoginRegisterViewModel
import pab.lop.illustrashopandroid.ui.view_model.MainViewModel
import pab.lop.illustrashopandroid.utils.WindowInfo
import pab.lop.illustrashopandroid.utils.rememberWindowInfo
import pablo_lonav.android.utils.ScreenNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.addLogAdapter(AndroidLogAdapter())

        val activityKiller: () -> Unit = { this.finish() }
        val loginRegisterViewModel by viewModels<LoginRegisterViewModel>()
        val mainViewModel by viewModels<MainViewModel>()

        setContent {
            IllustraShopAndroidTheme {

                val windowInfo = rememberWindowInfo()
                if(windowInfo.screenWidthInfo is WindowInfo.WindowType.Compat){
                    //TODO adapt on sizes
                }

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = ScreenNav.LoginScreen.route) {

                    /*** LOGIN SCREEN ***/
                    composable(
                        route = ScreenNav.LoginScreen.route
                    ) {
                        Login(
                            navController = navController,
                            loginRegisterViewModel = loginRegisterViewModel,
                            context = applicationContext
                        )
                        BackHandler(true) {
                            Toast.makeText(
                                applicationContext,
                                "BackButton Deshabilitado en el LOGIN",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                    /*** VALIDATE SCREEN ***/
                    composable(
                        route = ScreenNav.ValidateScreen.route
                    ) {
                        Validate(
                            navController = navController,
                            loginRegisterViewModel = loginRegisterViewModel,
                            context = applicationContext
                        )
                        BackHandler(true) {
                            Toast.makeText(
                                applicationContext,
                                "BackButton Deshabilitado en el LOGIN",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    /*** MAIN SCREEN ***/
                    composable(
                        route = ScreenNav.MainScreen.route
                    ) {
                        Main(
                            navController = navController,
                            mainViewModel = mainViewModel,
                            context = applicationContext
                        )
                        BackHandler(true) {
                            Toast.makeText(
                                applicationContext,
                                "BackButton Deshabilitado en el LOGIN",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}