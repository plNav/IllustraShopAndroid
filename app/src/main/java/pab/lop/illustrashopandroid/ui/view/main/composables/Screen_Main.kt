package pab.lop.illustrashopandroid.ui.view.main.composables

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.*

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

    if (userSelected == null) userSelected = userDefaultNoAuth
    Logger.i(userSelected.toString())

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
            addShoppingCart = addShoppingCart
        )

    if (popUpDetailsOpen.value) {
        PopUpDetails(
            mainViewModel = mainViewModel,
            scope = scope,
            popUpDetailsOpen = popUpDetailsOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            snackbarHostState = snackbarHostState,
            addShoppingCart = addShoppingCart
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
) {

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackBar(snackbarHostState) },
        drawerBackgroundColor = MaterialTheme.colors.primaryVariant,
        drawerShape = customShape(),
        drawerContent = { /* TODO DRAWER CONTENT --> Opciones: CerrarSesion, Configuracion, Filter * Family */ },
        topBar = {
            TopAppBar(
                verticalGradient = verticalGradient,
                scope = scope,
                scaffoldState = scaffoldState,
                snackbarHostState = snackbarHostState, addShoppingCart = addShoppingCart
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




