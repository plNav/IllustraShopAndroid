package pab.lop.illustrashopandroid.ui.view.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.R

@Composable
fun TopAppBar(
    verticalGradient: Brush,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    snackbarHostState: SnackbarHostState,
    addShoppingCart: MutableState<Boolean>
) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.Transparent,
        modifier = Modifier.background(verticalGradient),
        title = {
            Text(
                text = stringResource(R.string.illustrashop),
                modifier = Modifier
                    .padding(30.dp, 0.dp, 0.dp, 0.dp),
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    Logger.d("Click en options icon")
                    scope.launch { scaffoldState.drawerState.open() }
                }) {
                Icon(Icons.Filled.Menu, contentDescription = null, tint = Color.White)
            }
        },
        actions = {
            IconButton(
                onClick = {
                    /* TODO ICON BUTTON ACTION --> IR AL CARRITO */
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        snackbarHostState.showSnackbar("")
                    }
                }
            ) {
                Icon(
                    if(addShoppingCart.value) Icons.Filled.AddShoppingCart else Icons.Filled.ShoppingCart,
                    contentDescription = "ShoppingCart",
                    tint = /*if(addShoppingCart.value) Color.Yellow else*/ Color.White,
                )
            }
        }
    )
}