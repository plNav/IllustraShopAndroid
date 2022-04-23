package pab.lop.illustrashopandroid.ui.view.main.composables

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
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

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {SnackbarHostState()}

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
            verticalGradient = verticalGradient
        )

    if (popUpDetailsOpen.value) {
        PopUpDetails(
            mainViewModel = mainViewModel,
            scope = scope,
            popUpDetailsOpen = popUpDetailsOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            snackbarHostState = snackbarHostState
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
) {

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { SnackBar(snackbarHostState) },
        drawerBackgroundColor = MaterialTheme.colors.primaryVariant,
        drawerShape = customShape(),
        drawerContent = { /* TODO DRAWER CONTENT --> Opciones: CerrarSesion, Configuracion, Filter * Family */ },
        topBar = { TopAppBar(verticalGradient, scope, scaffoldState, snackbarHostState) }
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

@Composable
fun PopUpDetails(
    mainViewModel: MainViewModel,
    scope: CoroutineScope,
    popUpDetailsOpen: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradient: Brush,
    snackbarHostState: SnackbarHostState
) {
    var scale by remember { mutableStateOf(1f) }

    Dialog(
        onDismissRequest = {
        popUpDetailsOpen.value = false
    }) {

        Surface(
            modifier = Modifier
                .padding(customSpacing.small)
                .wrapContentHeight(),

            shape = RoundedCornerShape(5.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {


                    /************ TITLE ************/
                    Text(
                        text = productSelected?.name ?: "Error",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            .padding(12.dp)
                            .clickable(onClick = { })
                    )

                    /************ CLOSE ************/
                    IconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent),
                        onClick = {
                            popUpDetailsOpen.value = false
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar("")
                            }
                        },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = stringResource(R.string.Close)
                        )
                    }
                }

                Spacer(
                    modifier = androidx.compose.ui.Modifier.height(
                        customSpacing.mediumSmall
                    )
                )

                Box{
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("$URL_HEAD_IMAGES${productSelected!!.image}")
                            .crossfade(true)
                            .crossfade(1000)
                            .build(),
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.loading_image),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f)
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale
                            )
                            .pointerInput(Unit) {
                                detectTransformGestures { _, _, zoom, _ ->
                                    scale = when {
                                        scale < 0.5f -> 0.5f
                                        scale > 3f -> 3f
                                        else -> scale * zoom
                                    }
                                }
                            }
                        //   modifier = Modifier.fillMaxSize(0.8f)
                    )
                }
                Text("") // ÑAPAS
            }
        }
    }
}


