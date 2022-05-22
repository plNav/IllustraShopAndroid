package pab.lop.illustrashopandroid

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.MobileAds
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.ui.theme.IllustraShopAndroidTheme
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.login_register.composables.Login
import pab.lop.illustrashopandroid.ui.view.main.composables.Main
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.ui.view.admin.composables.*
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.ui.view.login_register.composables.Register
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.ui.view.main.composables.ShoppingCart
import pab.lop.illustrashopandroid.ui.view.main.composables.WishList
import pab.lop.illustrashopandroid.utils.WindowInfo
import pab.lop.illustrashopandroid.utils.rememberWindowInfo
import pab.lop.illustrashopandroid.utils.ScreenNav

class MainActivity : ComponentActivity() {

    private lateinit var customSpacing: Spacing

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.addLogAdapter(AndroidLogAdapter())

        //val activityKiller: () -> Unit = { this.finish() }

        val loginRegisterViewModel by viewModels<LoginRegisterViewModel>()
        val mainViewModel by viewModels<MainViewModel>()
        val adminViewModel by viewModels<AdminViewModel>()

        setContent {

            IllustraShopAndroidTheme {

                //Ads
                MobileAds.initialize(this)

                // Color of status bar
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = MaterialTheme.colors.secondary)

                // Spacing depending on window size
                val windowInfo = rememberWindowInfo()
                customSpacing = when (windowInfo.screenWidthInfo) {
                    is WindowInfo.WindowType.Compat -> Spacing.SpacingCompact
                    is WindowInfo.WindowType.Medium -> Spacing.SpacingMedium
                    is WindowInfo.WindowType.Expanded -> Spacing.SpacingExtended
                }

                /*** NAVIGATION GRAPH ***/

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenNav.LoginScreen.route
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
                        BackHandler(true) { } // TRUE == ENABLED
                    }

                    /*** REGISTER SCREEN ***/
                    composable(
                        route = "${ScreenNav.RegisterScreen.route}/{isEditionMode}",
                        arguments = listOf(navArgument("isEditionMode") { type = NavType.BoolType })
                    ) {
                        Register(
                            isEditionMode = it.arguments!!.getBoolean("isEditionMode"),
                            navController = navController,
                            loginRegisterViewModel = loginRegisterViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
                        )
                        BackHandler(false) { }
                    }

                    /*** MAIN SCREEN ***/
                    composable(
                        route = ScreenNav.MainScreen.route
                    ) {
                        Main(
                            navController = navController,
                            mainViewModel = mainViewModel,
                            adminViewModel = adminViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
                        )
                        BackHandler(true) { }
                    }

                    /*** SHOPPING CART SCREEN ***/
                    composable(
                        route = ScreenNav.ShoppingCartScreen.route
                    ) {
                        ShoppingCart(
                            navController = navController,
                            mainViewModel = mainViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
                        )
                        BackHandler(false) { }
                    }

                    /*** WISHLIST SCREEN ***/
                    composable(
                        route = ScreenNav.WishScreen.route
                    ) {
                        WishList(
                            navController = navController,
                            mainViewModel = mainViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
                        )
                        BackHandler(false) { }
                    }

                    /*** UPLOAD SCREEN ***/
                    composable(
                        route = ScreenNav.ImageUploadScreen.route
                    ) {
                        Image_Upload(
                            navController = navController,
                            adminViewModel = adminViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
                        )
                        BackHandler(true) { }
                    }

                    /*** EDIT PRODUCT SCREEN ***/
                    composable(
                        route = ScreenNav.ProductEditScreen.route
                    ) {
                        Edit_Product(
                            navController = navController,
                            adminViewModel = adminViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
                        )
                        BackHandler(false) { }
                    }

                    /*** ADMIN SCREEN ***/
                    composable(
                        route = ScreenNav.AdminScreen.route
                    ) {
                        Admin_Screen(
                            navController = navController,
                            adminViewModel = adminViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing
                        )
                        BackHandler(false) { }
                    }

                    /*** ORDER SCREEN ***/
                    composable(
                        route = "${ScreenNav.OrderScreen.route}/{isAdmin}",
                        arguments = listOf(navArgument("isAdmin") { type = NavType.BoolType })
                    ) {
                        OrderStart(
                            navController = navController,
                            adminViewModel = adminViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing,
                            isAdmin = it.arguments!!.getBoolean("isAdmin")
                        )
                        BackHandler(false) { }
                    }

                    /*** ANALYTICIS  SCREEN ***/
                    composable(
                        route = ScreenNav.AnalyticsScreen.route
                    ) {
                        Analytics(
                            navController = navController,
                            adminViewModel = adminViewModel,
                            context = applicationContext,
                            customSpacing = customSpacing,
                        )
                        BackHandler(false) { }
                    }

                }
            }
        }
    }
}