package pab.lop.illustrashopandroid.ui.view.main.composables

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.*
import pab.lop.illustrashopandroid.utils.ScreenNav

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Main(
    navController: NavController,
    mainViewModel: MainViewModel,
    context: Context,
    customSpacing: Spacing
) {
    val loadProductsFamily = remember { mutableStateOf(false) }
    val startLoading = remember { mutableStateOf(false) }
    val popUpDetailsOpen = remember { mutableStateOf(false) }
    val addShoppingCart = remember { mutableStateOf(false) }

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

    val verticalGradientIncomplete = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onSecondary, Color.DarkGray),
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
            customSpacing = customSpacing
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
            context = context
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
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
) {

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackBar(snackbarHostState) },
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
                mainViewModel = mainViewModel
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
fun MainDrawer(
    navController: NavController,
    context: Context,
    verticalGradient: Brush,
    scaffoldState: ScaffoldState,
    customSpacing: Spacing,
    scope: CoroutineScope,
    mainViewModel: MainViewModel,
    verticalGradientDisabled: Brush
) {
    //Text("hola", modifier = Modifier.fillMaxWidth())
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.65f)
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        /*** USER INFO ***/
        Card(Modifier.fillMaxWidth()) {
            Text(if (userSelected == userDefaultNoAuth) stringResource(R.string.not_logged) else  userSelected!!.username)
        }

        /*** EDIT PERSONAL INFO ***/
        Text(
            text =
                if (userSelected == userDefaultNoAuth) stringResource(R.string.register)
                else stringResource(R.string.edit_personal_info),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = {
                    if (userSelected == userDefaultNoAuth) navController.navigate(ScreenNav.RegisterScreen.withArgs(false))
                    else navController.navigate(ScreenNav.RegisterScreen.withArgs(true))
                })
        )

        /*** WISHLIST ***/
        Text(
            text = stringResource(R.string.new_product),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = { navController.navigate(ScreenNav.ImageUploadScreen.route) })
        )

        /*** DELIVERS ***/
        Text(
            text = stringResource(R.string.new_product),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = { navController.navigate(ScreenNav.ImageUploadScreen.route) })
        )

        /*** ADMIN SETTINGS IF USER ROL == ADMIN ***/
        Text(
            text = stringResource(R.string.menu_admin),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = { navController.navigate(ScreenNav.AdminScreen.route) })
        )

        /*** CLOSE SESSION - INIT SESSION IF NO AUTH USER ***/
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = {
                    userSelected = null
                    navController.navigate(ScreenNav.LoginScreen.route)
                }),
            text =
                if (userSelected == userDefaultNoAuth) stringResource(R.string.login)
                else stringResource(R.string.logout),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            )
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




