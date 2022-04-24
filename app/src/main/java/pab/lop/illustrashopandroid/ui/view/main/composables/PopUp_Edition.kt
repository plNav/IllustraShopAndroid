package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.URL_HEAD_IMAGES

@Composable
fun PopUpEdition(
    mainViewModel: MainViewModel,
    navController: NavController,
    context: Context,
    verticalGradient: Brush,
    openPopUpEdition: MutableState<Boolean>,
    customSpacing: Spacing,
    currentLine: MutableState<product_shopping_response?>
) {
    Dialog(
        onDismissRequest = {
            openPopUpEdition.value = false
        }) {

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
                        text = "${currentLine.value!!.name}",
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

                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
                    Card(modifier = Modifier.height(150.dp).width(150.dp)){
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("$URL_HEAD_IMAGES${currentLine.value!!.image}")
                                .crossfade(true)
                                .crossfade(1000)
                                .build(),
                            contentDescription = null,
                            //placeholder = painterResource(id = R.drawable.loading_image),
                            //   modifier = Modifier.fillMaxSize(0.8f)
                        )
                   }
                    Spacer(modifier = Modifier.height(customSpacing.small))

                    Row(){
                        Text("Amount")
                        Text("Button +")
                        Text("Button -")
                    }
                    Spacer(modifier = Modifier.height(customSpacing.mediumSmall))

                }



                /************ SAVE ************/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {

                            mainViewModel.updateProductShopping(currentLine.value!!) {
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