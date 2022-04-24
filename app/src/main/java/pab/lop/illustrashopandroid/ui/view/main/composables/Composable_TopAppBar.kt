package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import android.widget.Toast
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
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.currentShoppingProducts
import pab.lop.illustrashopandroid.utils.shoppingCartSelected
import pab.lop.illustrashopandroid.utils.userDefaultNoAuth
import pab.lop.illustrashopandroid.utils.userSelected
import pab.lop.illustrashopandroid.utils.ScreenNav

@Composable
fun TopAppBar(
    verticalGradient: Brush,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    addShoppingCart: MutableState<Boolean>,
    navController: NavController,
    mainViewModel: MainViewModel,
    context: Context
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
                    if (userSelected == userDefaultNoAuth) Toast.makeText(
                        context,
                        context.getString(R.string.login_needed),
                        Toast.LENGTH_SHORT
                    ).show()
                    else mainViewModel.getAllProductShopping(shoppingCartSelected!!._id) {
                        currentShoppingProducts = mainViewModel.currentProductsShopping.toMutableList()
                        if (currentShoppingProducts.isEmpty()) Toast.makeText(
                            context,
                            context.getString(R.string.empty_cart),
                            Toast.LENGTH_SHORT
                        ).show()
                        else navController.navigate(ScreenNav.ShoppingCartScreen.route)
                    }
                }
            ) {
                Icon(
                    if (addShoppingCart.value) Icons.Filled.AddShoppingCart else Icons.Filled.ShoppingCart,
                    contentDescription = "ShoppingCart",
                    tint = /*if(addShoppingCart.value) Color.Yellow else*/ Color.White,
                )
            }
        }
    )
}