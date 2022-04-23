package pab.lop.illustrashopandroid.ui.view.main.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.URL_HEAD_IMAGES
import pab.lop.illustrashopandroid.utils.productSelected

@Composable
fun PopUpDetails(
    mainViewModel: MainViewModel,
    scope: CoroutineScope,
    popUpDetailsOpen: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradient: Brush,
    snackbarHostState: SnackbarHostState,
    addShoppingCart: MutableState<Boolean>,
) {
    var scale by remember { mutableStateOf(1f) }
    val painter = rememberAsyncImagePainter(productSelected!!.image)


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
                        text = "${productSelected!!.name} - ${productSelected!!.price} €",
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

                ZoomableImage(isRotation = false)


                /************ ADD TO SHOPPING CART ************/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            addShoppingCart.value = true
                            popUpDetailsOpen.value = false
                            scope.launch {
                                snackbarHostState.currentSnackbarData?.dismiss()
                                snackbarHostState.showSnackbar("")
                            }
                        })
                ) {

                    Text(
                        text = stringResource(R.string.addShopping),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradient)
                            .padding(12.dp)

                    )
                }

                /************ ADD TO WISHLIST ************/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                        })
                ) {

                    Text(
                        text = stringResource(R.string.addWish),
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

@Composable
fun ZoomableImage(
    maxScale: Float = .30f,
    minScale: Float = 3f,
    contentScale: ContentScale = ContentScale.Fit,
    isRotation: Boolean = false,
    isZoomable: Boolean = true
) {
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(1f) }
    val offsetX = remember { mutableStateOf(1f) }
    val offsetY = remember { mutableStateOf(1f) }

    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .background(Color.Transparent)
            .pointerInput(Unit) {
                if (isZoomable) {
                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown()
                            do {
                                val event = awaitPointerEvent()
                                scale.value *= event.calculateZoom()
                                if (scale.value > 1) {
                                    val offset = event.calculatePan()
                                    offsetX.value += offset.x
                                    offsetY.value += offset.y
                                    rotationState.value += event.calculateRotation()
                                } else {
                                    scale.value = 1f
                                    offsetX.value = 1f
                                    offsetY.value = 1f
                                }
                            } while (event.changes.any { it.pressed })
                        }
                    }
                }
            }

    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("$URL_HEAD_IMAGES${productSelected!!.image}")
                .crossfade(true)
                .crossfade(1000)
                .build(),
            contentDescription = null,
            contentScale = contentScale,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    if (isZoomable) {
                        scaleX = maxOf(maxScale, minOf(minScale, scale.value))
                        scaleY = maxOf(maxScale, minOf(minScale, scale.value))
                        if (isRotation) {
                            rotationZ = rotationState.value
                        }
                        translationX = offsetX.value
                        translationY = offsetY.value
                    }
                }
        )
    }
}