package pab.lop.illustrashopandroid.ui.view.admin.composables

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.theme.SurfaceAlmostBlack
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.utils.*

@Composable
fun PopUp_EditOrder(
    isEditOpen: MutableState<Boolean>,
    orderSelected: MutableState<order_response?>,
    adminViewModel: AdminViewModel,
    verticalGradient: Brush,
    navController: NavController,
    customSpacing: Spacing,
    isAdmin: Boolean
) {

    val filter = remember { mutableStateOf(orderSelected.value!!.status) }
    val pending: String = stringResource(R.string.PENDING)
    val sent: String = stringResource(R.string.SENT)

    Dialog(
        onDismissRequest = { isEditOpen.value = false }
    ) {
        Surface(
            modifier = Modifier
                .padding(customSpacing.small),
            shape = RoundedCornerShape(5.dp),
            color = Color.White
        ) {
            Column() {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {


                    /************ TITLE ************/
                    Text(
                        text = "${orderSelected.value!!.user.username} - ${orderSelected.value!!.total} €",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            .padding(12.dp)
                    )


                    /************ CLOSE ************/
                    IconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent),
                        onClick = { isEditOpen.value = false },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = stringResource(R.string.Close)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(
                        customSpacing.mediumSmall
                    )
                )

                /************ USER INFO ************/

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(customSpacing.small)
                ) {
                    Text(
                        style = MaterialTheme.typography.body2,
                        text = """
                            ${stringResource(R.string.name)} : ${orderSelected.value!!.user.name}
                            ${stringResource(R.string.lastName)} : ${orderSelected.value!!.user.last_name}
                            ${stringResource(R.string.email)} : ${orderSelected.value!!.user.email}
                            ${stringResource(R.string.address)} : ${orderSelected.value!!.user.address}
                            ${stringResource(R.string.country)} : ${orderSelected.value!!.user.country} 
                            ${stringResource(R.string.postalCode)} : ${orderSelected.value!!.user.postal_code} 
                            ${stringResource(R.string.phone)} : ${orderSelected.value!!.user.phone}
                    """.trimIndent()
                    )

                    Spacer(modifier = Modifier.height(customSpacing.small))

                    Text(
                        style = MaterialTheme.typography.body1,
                        text = "${stringResource(R.string.products)} (${orderSelected.value!!.products.count()}) :"
                    )

                    Spacer(modifier = Modifier.height(customSpacing.small))

                }

                /************ PRODUCTS INFO ************/

                LazyColumn(Modifier.fillMaxHeight( if (isAdmin) 0.3f else 0.6f)) {
                    itemsIndexed(orderSelected.value!!.products) { _, product ->
                        Card(Modifier.padding(customSpacing.small)) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(customSpacing.small)
                            ) {
                               Box(
                                   modifier = Modifier
                                       .height(customSpacing.extraLarge)
                                       .width(customSpacing.extraLarge)
                               ){
                                   SubcomposeAsyncImage(
                                       model = ImageRequest.Builder(LocalContext.current)
                                           .data("$URL_HEAD_IMAGES${product.image}")
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

                                Text(
                                    modifier = Modifier.padding(customSpacing.small),
                                    text = """
                                        ${stringResource(R.string.product)} : ${product.name}
                                        ${stringResource(R.string.cant)} : ${product.amount}
                            """.trimIndent()
                                )
                            }
                        }
                    }
                }


                /************ OPTIONS ************/

                Card(
                    modifier = Modifier.padding(customSpacing.small),
                    border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(customSpacing.small)
                    ) {


                        if(isAdmin){
                            Button(
                                modifier = Modifier
                                    .height(customSpacing.superLarge)
                                    .fillMaxWidth(),
                                colors =
                                if (filter.value == PENDING) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                                else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


                                onClick = {
                                    filter.value = PENDING
                                },
                            ) {
                                Text(text = pending, color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(customSpacing.small))

                            Button(
                                modifier = Modifier
                                    .height(customSpacing.superLarge)
                                    .fillMaxWidth(),
                                colors =
                                if (filter.value == SENT) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                                else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),
                                onClick = {
                                    filter.value = SENT
                                },
                            ) {
                                Text(text = sent, color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(customSpacing.small))

                            Button(
                                enabled = isAdmin,
                                modifier = Modifier
                                    .height(customSpacing.superLarge)
                                    .fillMaxWidth(),
                                colors =
                                if (filter.value == ENDED) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                                else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),
                                onClick = {
                                    filter.value = ENDED
                                },
                            ) {
                                Icon(Icons.Filled.Done, contentDescription = null, tint = Color.White)
                            }
                        }else{
                            Text(text = when(orderSelected.value!!.status){
                                PENDING -> stringResource(R.string.PENDING)
                                SENT -> stringResource(R.string.SENT)
                                ENDED -> stringResource(R.string.SENT)
                                else -> stringResource(R.string.error)
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(customSpacing.small))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        //.fillMaxSize()
                        .padding(horizontal = customSpacing.mediumSmall)

                ) {

                    /************ OK ************/
                    Text(
                        text = stringResource(R.string.Ok),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradient)
                            .padding(12.dp)
                            .clickable(onClick = {

                                if(isAdmin){
                                    orderSelected.value!!.status = filter.value


                                    //TODO UPDATE ORDER
                                    adminViewModel.getOrders {
                                        isEditOpen.value = false
                                        allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                                        navController.navigate(ScreenNav.OrderScreen.withArgs(true))
                                    }
                                }else{
                                    //TODO GET USER ORDERS X2
                                    navController.navigate(ScreenNav.OrderScreen.withArgs(false))

                                }

                            })
                    )

                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.mediumMedium
                        )
                    )
                }
            }
        }
    }
}