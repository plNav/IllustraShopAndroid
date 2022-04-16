package pab.lop.illustrashopandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.ui.theme.IllustraShopAndroidTheme
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.composables.Image_Upload
import pab.lop.illustrashopandroid.ui.view.login_register.Login
import pab.lop.illustrashopandroid.ui.view.login_register.Validate
import pab.lop.illustrashopandroid.ui.view.main.composables.Main
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.WindowInfo
import pab.lop.illustrashopandroid.utils.rememberWindowInfo
import pablo_lonav.android.utils.ScreenNav

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.addLogAdapter(AndroidLogAdapter())

        //val activityKiller: () -> Unit = { this.finish() }
        var customSpacing : Spacing

        val loginRegisterViewModel by viewModels<LoginRegisterViewModel>()
        val mainViewModel by viewModels<MainViewModel>()
        val adminViewModel by viewModels<AdminViewModel>()


        setContent {
            IllustraShopAndroidTheme {

                // Color of status bar
                val systemUiController = rememberSystemUiController()
                    systemUiController.setSystemBarsColor(
                        color = MaterialTheme.colors.secondary
                    )

                // Spacing depending on window size
                val windowInfo = rememberWindowInfo()
                customSpacing = when (windowInfo.screenWidthInfo) {
                    is WindowInfo.WindowType.Compat ->  Spacing.SpacingCompact
                    is WindowInfo.WindowType.Medium ->  Spacing.SpacingMedium
                    is WindowInfo.WindowType.Expanded ->  Spacing.SpacingExtended
                }

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenNav.MainScreen.route
                ) {

                    /*** LOGIN SCREEN ***/
                    composable(
                        route = ScreenNav.LoginScreen.route
                    ) {
                        Login(
                            navController = navController,
                            loginRegisterViewModel = loginRegisterViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
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
                            context = applicationContext,
                            customSpacing = customSpacing
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
                            context = applicationContext,
                            customSpacing = customSpacing
                        )
                        BackHandler(true) {
                            Toast.makeText(
                                applicationContext,
                                "BackButton Deshabilitado en el LOGIN",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    /*** UPLOAD SCREEN ***/
                    composable(
                        route = ScreenNav.Image_Upload.route
                    ) {
                        Image_Upload(
                            navController = navController,
                            adminViewModel = adminViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
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