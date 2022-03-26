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
import pab.lop.illustrashopandroid.ui.view_model.LoginRegisterViewModel
import pablo_lonav.android.utils.ScreenNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.addLogAdapter(AndroidLogAdapter())

        val activityKiller: () -> Unit = { this.finish() }
        val loginRegisterViewModel by viewModels<LoginRegisterViewModel>()

        setContent {
            IllustraShopAndroidTheme {
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
                }
            }
        }
    }
}