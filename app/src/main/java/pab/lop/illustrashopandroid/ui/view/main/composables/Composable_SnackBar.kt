package pab.lop.illustrashopandroid.ui.view.main.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SnackBar(snackbarHostState: SnackbarHostState) {
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
                            text = "Added to Shopping Cart",
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
                                contentDescription = "Ver comentario",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}
