package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.ScreenNav
import pab.lop.illustrashopandroid.utils.URL_HEAD_IMAGES

@Composable
fun PopUpEdition(
    mainViewModel: MainViewModel,
    navController: NavController,
    context: Context,
    verticalGradient: Brush,
    openPopUpEdition: MutableState<Boolean>,
    customSpacing: Spacing,
    currentLine: MutableState<product_shopping_response?>,
    isSaved: MutableState<Boolean>
) {
    val amount = remember { mutableStateOf(currentLine.value!!.amount) }
    val deleteConfirmation = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { openPopUpEdition.value = false }
    ) {
        Surface(
            modifier = Modifier
                .padding(customSpacing.small)
                .wrapContentHeight(),
            shape = RoundedCornerShape(5.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(brush = verticalGradient)
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {

                    /************ TITLE ************/
                    Text(
                        text = "${currentLine.value!!.name} - ${amount.value}",
                        textAlign = TextAlign.Start,
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
                            openPopUpEdition.value = false

                        },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = stringResource(R.string.Close)
                        )
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(
                            onClick = {
                                amount.value++
                                deleteConfirmation.value = false
                            }
                        ) {
                            Icon(
                                Icons.Filled.AddCircle,
                                tint = MaterialTheme.colors.onSecondary,
                                contentDescription = stringResource(R.string.Close)
                            )
                        }

                        Card(
                            modifier = Modifier
                                .height(150.dp)
                                .width(150.dp)
                        ) {
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("$URL_HEAD_IMAGES${currentLine.value!!.image}")
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

                        IconButton(
                            onClick = {
                                if (amount.value == 1f) {
                                    if (deleteConfirmation.value) {
                                        //TODO DELETE PRODUCT
                                        openPopUpEdition.value = false
                                    } else {
                                        deleteConfirmation.value = true
                                        Toast.makeText(context, context.getString(R.string.pressAgainDelete), Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                } else {
                                    amount.value--
                                }
                            },
                        ) {
                            Icon(
                                Icons.Filled.RemoveCircle,
                                tint = MaterialTheme.colors.onSecondary,
                                contentDescription = stringResource(R.string.Close)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(customSpacing.small))


                }


                /************ SAVE ************/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            currentLine.value!!.amount = amount.value
                            mainViewModel.updateProductShopping(currentLine.value!!) {
                                openPopUpEdition.value = false
                                isSaved.value = true
                                navController.navigate(ScreenNav.ShoppingCartScreen.route)
                                Logger.i("Update OK")
                            }

                        })
                ) {

                    Text(
                        text = stringResource(R.string.save),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradient)
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}