package pablo_lonav.android.utils

/** Clase para almacenar las rutas, con y sin parametros */
sealed class ScreenNav(val route: String) {

    object LoginScreen : ScreenNav("login_screen")
    object RegisterScreen : ScreenNav("register_screen")

    object MainScreen : ScreenNav("main_screen")

    object Image_Upload : ScreenNav("image_upload")
    object Admin_Screen : ScreenNav("admin_screen")
    object Product_Edit : ScreenNav("product_edit")


    fun withArgs(vararg args: String) : String{
        return buildString{
            append(route)
            args.forEach { arg -> append("/$arg") }
        }
    }
}