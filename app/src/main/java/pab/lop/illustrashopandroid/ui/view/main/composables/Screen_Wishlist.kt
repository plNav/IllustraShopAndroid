package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
    val addToShoppingCart = remember {mutableStateOf(false)}


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

    if(openPopUpDetails.value){
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
            navController = navController
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(customSpacing.small)
        ) {
            itemsIndexed(wishlistProducts) { _, item ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface)
                        .padding(customSpacing.mediumSmall)
                        .clickable(onClick = {
                            productSelected = item
                            openPopUpDetails.value = true
                        })
                ) {
                    Text(item.name)
                }
            }
        }
    }
}
