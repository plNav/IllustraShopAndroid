package pab.lop.illustrashopandroid.ui.view.main.composables

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.*

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Main(
    navController: NavController,
    mainViewModel: MainViewModel,
    context: Context,
    customSpacing: Spacing,
    adminViewModel: AdminViewModel,
    loginRegisterViewModel: LoginRegisterViewModel
) {
    val loadProductsFamily = remember { mutableStateOf(false) }
    val startLoading = remember { mutableStateOf(false) }
    val popUpDetailsOpen = remember { mutableStateOf(false) }
    val addShoppingCart = remember { mutableStateOf(false) }
    val isShoppingCart = remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (userSelected == null) {
        userSelected = userDefaultNoAuth
        shoppingCartSelected = shoppingCartDefaultNoAuth
    }
    Logger.i("User Selected -> $userSelected \nShopping Cart Selected -> $shoppingCartSelected")

    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )
    val verticalGradientDisabled = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onError, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )


    if (!startLoading.value) {
        startLoading.value = true
        mainViewModel.getProductsFamily {
            familyProducts = mainViewModel.familyProductsResponse
            Logger.d("Families => ${familyProducts.keys}")
            loadProductsFamily.value = true
        }
        if (userSelected != userDefaultNoAuth) {
            mainViewModel.getShoppingCart(userSelected!!._id) {
                shoppingCartSelected = mainViewModel.currentShoppingCartResponse
                mainViewModel.getAllProductShopping(shoppingCartSelected!!._id) {
                    currentShoppingProducts = mainViewModel.currentProductsShopping
                }
            }
        }
    }



    if (loadProductsFamily.value)
        MainStart(
            navController = navController,
            snackbarHostState = snackbarHostState,
            scope = scope,
            scaffoldState = rememberScaffoldState(),
            applicationContext = context,
            mainViewModel = mainViewModel,
            familyProducts = familyProducts,
            popUpDetailsOpen = popUpDetailsOpen,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            addShoppingCart = addShoppingCart,
            customSpacing = customSpacing,
            adminViewModel = adminViewModel,
            loginRegisterViewModel = loginRegisterViewModel,
            isShoppingCart = isShoppingCart
        )

    if (popUpDetailsOpen.value) {
        PopUpDetails(
            mainViewModel = mainViewModel,
            scope = scope,
            popUpDetailsOpen = popUpDetailsOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            snackbarHostState = snackbarHostState,
            addShoppingCart = addShoppingCart,
            context = context,
            verticalGradientDisabled = verticalGradientDisabled,
            isWishList = false,
            navController = navController,
            isShoppingCart = isShoppingCart
        )
    }
}


@Composable
fun MainStart(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    applicationContext: Context,
    mainViewModel: MainViewModel,
    familyProducts: HashMap<String, List<product_stock_response>>,
    popUpDetailsOpen: MutableState<Boolean>,
    verticalGradient: Brush,
    addShoppingCart: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradientDisabled: Brush,
    adminViewModel: AdminViewModel,
    loginRegisterViewModel: LoginRegisterViewModel,
    isShoppingCart: MutableState<Boolean>
) {

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackBar(snackbarHostState, isShoppingCart) },
        drawerBackgroundColor = MaterialTheme.colors.primaryVariant,
        drawerShape = customShape(),
        drawerContent = {
            MainDrawer(
                navController = navController,
                context = applicationContext,
                verticalGradient = verticalGradient,
                verticalGradientDisabled = verticalGradientDisabled,
                scaffoldState = scaffoldState,
                customSpacing = customSpacing,
                scope = scope,
                mainViewModel = mainViewModel,
                adminViewModel = adminViewModel,
                loginRegisterViewModel = loginRegisterViewModel
            )
        },
        topBar = {
            TopAppBar(
                verticalGradient = verticalGradient,
                scope = scope,
                scaffoldState = scaffoldState,
                addShoppingCart = addShoppingCart,
                mainViewModel = mainViewModel,
                navController = navController,
                context = applicationContext
            )
        }
    ) {
        Body(familyProducts, popUpDetailsOpen)
    }
}




@Composable
fun customShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
                left = 0f,
                top = 0f,
                right = size.width * 2 / 3,
                bottom = size.height
            )
        )
    }
}




