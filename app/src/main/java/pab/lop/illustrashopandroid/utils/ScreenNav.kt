package pab.lop.illustrashopandroid.utils

/** Clase para almacenar las rutas, con y sin parametros */
sealed class ScreenNav(val route: String) {

    object LoginScreen : ScreenNav("login_screen")
    object RegisterScreen : ScreenNav("register_screen")

    object MainScreen : ScreenNav("main_screen")
    object ShoppingCartScreen : ScreenNav("shopping_cart_screen")
    object WishScreen : ScreenNav("wish_screen")

    object ImageUploadScreen : ScreenNav("image_upload_screen")
    object AdminScreen : ScreenNav("admin_screen")
    object ProductEditScreen : ScreenNav("product_edit_screen")
    object OrderScreen : ScreenNav("order_screen")
    object AnalyticsScreen : ScreenNav("analytics_screen")

    fun withArgs(vararg args: Any) : String{
        return buildString{
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}