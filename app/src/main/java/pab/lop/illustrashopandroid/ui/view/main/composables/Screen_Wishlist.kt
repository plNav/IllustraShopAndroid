package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.*

@Composable
fun WishList(
    mainViewModel: MainViewModel,
    navController: NavController,
    customSpacing: Spacing,
    context: Context
) {
    val scaffoldState = rememberScaffoldState()
    val currentLine = remember { mutableStateOf<product_stock_response?>(null) }
    val openPopUpDetails = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val addToShoppingCart = remember { mutableStateOf(false) }
    val isShoppingCart = remember { mutableStateOf(true) }


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

    if (openPopUpDetails.value) {
        PopUpDetails(
            mainViewModel = mainViewModel,
            scope = scope,
            popUpDetailsOpen = openPopUpDetails,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            snackbarHostState = snackbarHostState,
            addShoppingCart = addToShoppingCart,
            context = context,
            verticalGradientDisabled = verticalGradientDisabled,
            isWishList = true,
            navController = navController,
            isShoppingCart = isShoppingCart
        )
    }



    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier.background(verticalGradient),
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = stringResource(R.string.whislist).uppercase(),
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 0.dp, 0.dp),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(ScreenNav.MainScreen.route)
                        }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(customSpacing.mediumMedium)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            itemsIndexed(wishlistProducts) { _, item ->
                Card(
                    modifier = Modifier.padding(vertical = customSpacing.mediumSmall, horizontal = customSpacing.small),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .background(verticalGradient)
                            .padding(customSpacing.small)
                            .clickable(onClick = {
                                productSelected = item
                                openPopUpDetails.value = true
                            }),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Card(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                                    .padding(customSpacing.small),
                                shape = RoundedCornerShape(15.dp),
                                border = BorderStroke(2.dp, Color.DarkGray),

                                ) {
                                SubcomposeAsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("$URL_HEAD_IMAGES${item.image}")
                                        .crossfade(true)
                                        .crossfade(1000)
                                        .build(),
                                    contentDescription = null,
                                    loading = { CircularProgressIndicator(modifier = Modifier.fillMaxSize()) },
                                    contentScale = ContentScale.Crop,
                                    error = {
                                        Image(
                                            painter = painterResource(id = R.drawable.loading_image),
                                            contentDescription = stringResource(R.string.error),
                                        )
                                    },
                                )
                            }


                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Text(text = item.name)
                                Spacer(modifier = Modifier.height(customSpacing.small))
                                Text(text = "${item.price}€")
                            }
                        }
                    }
                }
            }
        }
    }
}
