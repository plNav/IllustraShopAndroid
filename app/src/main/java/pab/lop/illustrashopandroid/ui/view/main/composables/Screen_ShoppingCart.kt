package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.order.order_request
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingCart(
    mainViewModel: MainViewModel,
    navController: NavController,
    context: Context,
    customSpacing: Spacing
) {
    val scaffoldState = rememberScaffoldState()
    val isSaved = remember { mutableStateOf(true) }
    val currentLine = remember { mutableStateOf<product_shopping_response?>(null) }
    val openPopUpEdition = remember { mutableStateOf(false) }
    val openPopUpComment = remember { mutableStateOf(false) }
    val comment = remember { mutableStateOf("") }

    val total = remember { mutableStateOf(0f) }

    val currentContext = LocalContext.current
    val addressNeeded = stringResource(R.string.address_needed)

    if (isSaved.value) {
        total.value = 0f
        for (item in currentShoppingProducts) {
            if (!item.bought) total.value += item.total
        }
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
            currentLine = currentLine,
            isSaved = isSaved
        )
    }
    if (openPopUpComment.value) {
        PopUpComment(
            verticalGradient = verticalGradient,
            openPopUpComment = openPopUpComment,
            customSpacing = customSpacing,
            comment = comment,
            isOrders = false
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
            )
        }
    ) {

        Column {
            CartHeader(customSpacing, total)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(5.dp)
            ) {
                CartBody(
                    currentLine = currentLine,
                    openPopUpEdition = openPopUpEdition,
                )
            }
            Spacer(modifier = Modifier.height(customSpacing.small))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ExtendedFloatingActionButtonExample(comment = comment, openPopUpComment = openPopUpComment)
            }

            Spacer(modifier = Modifier.height(customSpacing.small))


            /*** BUY NOW ***/
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val order = order_request(
                            user = userSelected!!,
                            products = currentShoppingProducts.filter { !it.bought },
                            total = total.value,
                            status = "PENDING",
                            comments = comment.value
                        )

                        if (userSelected!!.address.isEmpty()) {
                            Toast.makeText(context, addressNeeded, Toast.LENGTH_SHORT).show()
                        } else {
                            mainViewModel.createOrder(order) {
                                mainViewModel.markBoughtProducts(currentShoppingProducts) {
                                    linkToWebpage(currentContext, mainViewModel)
                                    navController.navigate(ScreenNav.MainScreen.route)
                                }
                            }
                        }
                    }
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


fun linkToWebpage(context: Context, mainViewModel: MainViewModel) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(mainViewModel.currentPayPalresponse)
    startActivity(context, openURL, null)
}

@Composable
fun ExtendedFloatingActionButtonExample(comment: MutableState<String>, openPopUpComment: MutableState<Boolean>) {
    ExtendedFloatingActionButton(

        text = {
            Text(
                text = if (comment.value.isEmpty()) stringResource(R.string.add_comment) else stringResource(R.string.comment_added),
                color = Color.White
            )
        },
        onClick = { openPopUpComment.value = true },
        icon = {
            Icon(
                imageVector = if (comment.value.isEmpty()) Icons.Filled.Comment else Icons.Filled.Check,
                tint = Color.White,
                contentDescription = ""
            )
        })
}










