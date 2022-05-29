package pab.lop.illustrashopandroid.ui.view.main.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pab.lop.illustrashopandroid.R

@Composable
fun SnackBar(snackbarHostState: SnackbarHostState, isShoppingCart: MutableState<Boolean>) {
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { snackbarHostState.currentSnackbarData?.dismiss() },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if(isShoppingCart.value)stringResource(R.string.added_shopping) else stringResource(R.string.added_wishlist),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(30.dp)
                                .fillMaxWidth(0.8f)
                        )
                        IconButton(
                            onClick = { snackbarHostState.currentSnackbarData?.dismiss() }
                        ) {
                            Icon(
                                Icons.Filled.HighlightOff,
                                contentDescription = stringResource(R.string.Close),
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}
