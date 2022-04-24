package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.currentShoppingProducts
import pab.lop.illustrashopandroid.utils.ScreenNav

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingCart(
    mainViewModel: MainViewModel,
    navController: NavController,
    context: Context,
    customSpacing: Spacing
) {
    val scaffoldState = rememberScaffoldState()
    val isEditionMode = remember { mutableStateOf(false) }
    val isSaved = remember { mutableStateOf(true) }
    val currentLine = remember { mutableStateOf<product_shopping_response?>(null) }
    val openPopUpEdition = remember { mutableStateOf(false) }

    val total = remember { mutableStateOf(0f) }

    if (isSaved.value) {
        total.value = 0f
        for (item in currentShoppingProducts) total.value += item.total
        isSaved.value = false
    }


    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )

    if (openPopUpEdition.value) {
        PopUpEdition(
            mainViewModel = mainViewModel,
            navController = navController,
            context = context,
            verticalGradient = verticalGradient,
            openPopUpEdition = openPopUpEdition,
            customSpacing = customSpacing,
            currentLine = currentLine
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
                        text = stringResource(R.string.shopping_cart).uppercase(),
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
                /*    actions = {
                        IconButton(
                            onClick = {
                                if (isEditionMode.value) {
                                    //TODO update
                                    isSaved.value = true
                                    Toast.makeText(context, "Updating....", Toast.LENGTH_SHORT).show()
                                }
                                isEditionMode.value = !isEditionMode.value
                            }
                        ) {
                            Icon(
                                if (isEditionMode.value) Icons.Filled.Save else Icons.Filled.Edit,
                                contentDescription = "ShoppingCart",
                                tint = *//*if(addShoppingCart.value) Color.Yellow else*//* Color.White,
                        )
                    }
                }*/
            )
        }
    ) {

        Column() {
            CartHeader(customSpacing, total)

            Card(
                //    backgroundColor = colorResource(id = R.color.gris_muy_claro),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(5.dp)
            ) {
                CartBody(currentLine, openPopUpEdition)
            }
            Spacer(modifier = Modifier.height(customSpacing.small))

            /*** BUY NOW ***/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        //TODO VALIDACION DE PAGO
                        navController.navigate(ScreenNav.PayScreen.route)
                    })
            ) {

                Text(
                    text = stringResource(R.string.buyNow),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1.copy(color = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush = verticalGradient)
                        .padding(12.dp)

                )
            }
            Spacer(modifier = Modifier.height(customSpacing.small))

        }
    }
}










