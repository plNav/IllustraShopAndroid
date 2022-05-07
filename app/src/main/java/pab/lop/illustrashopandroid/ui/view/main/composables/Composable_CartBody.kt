package pab.lop.illustrashopandroid.ui.view.main.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.utils.currentShoppingProducts

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartBody(
    currentLine: MutableState<product_shopping_response?>,
    openPopUpEdition: MutableState<Boolean>,
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(5),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(5.dp),
    ) {
        for (product in currentShoppingProducts) {
            if(!product.bought){
                item {
                    Card(

                        modifier = Modifier.combinedClickable(
                            onClick = {
                                currentLine.value = product
                                openPopUpEdition.value = true
                            }
                        )
                    ) {
                        Text(
                            text = "${product.amount}",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        )
                    }
                }
                item(span = { GridItemSpan(2) }) {
                    Card(
                        modifier = Modifier.combinedClickable(

                            onClick = {
                                currentLine.value = product
                                openPopUpEdition.value = true

                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = product.name,
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(10.dp)
                            )
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier.combinedClickable(

                            onClick = {
                                currentLine.value = product
                                openPopUpEdition.value = true
                            }
                        )
                    ) {
                        Text(
                            text = "${product.price}€",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        )
                    }
                }
                item {
                    Card(
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                currentLine.value = product
                                openPopUpEdition.value = true
                            }
                        )
                    ) {
                        Text(
                            text =
                            if (product.total.toString().length >= 5)
                                "${product.total.toString().substring(0, 4).toFloat()}€"
                            else "${product.total}€",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        )
                    }
                }
            }

        }
    }
}