package pab.lop.illustrashopandroid.ui.view.admin.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.theme.SurfaceAlmostBlack
import pab.lop.illustrashopandroid.utils.ENDED
import pab.lop.illustrashopandroid.utils.PENDING
import pab.lop.illustrashopandroid.utils.SENT

@Composable
fun Filters(filter: MutableState<String>, customSpacing: Spacing) {
    val all: String = stringResource(R.string.ALL)
    val pending: String = stringResource(R.string.PENDING)
    val sent: String = stringResource(R.string.SENT)

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(customSpacing.small)
    ) {

        Button(
            modifier = Modifier.height(customSpacing.superLarge),
            colors =
            if (filter.value == "") ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


            onClick = {
                filter.value = ""
            },
        ) {
            Text(text = all, color = Color.White)
        }
        Button(
            modifier = Modifier.height(customSpacing.superLarge),
            colors =
            if (filter.value == PENDING) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


            onClick = {
                filter.value = PENDING
            },
        ) {
            Text(text = pending, color = Color.White)
        }
        Button(
            modifier = Modifier.height(customSpacing.superLarge),
            colors =
            if (filter.value == SENT) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


            onClick = {
                filter.value = SENT
            },
        ) {
            Text(text = sent, color = Color.White)
        }
        Button(
            modifier = Modifier.height(customSpacing.superLarge),
            colors =
            if (filter.value == ENDED) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = SurfaceAlmostBlack),


            onClick = {
                filter.value = ENDED
            },
        ) {
            Icon(Icons.Filled.Done, contentDescription = null, tint = Color.White)
        }

    }
}