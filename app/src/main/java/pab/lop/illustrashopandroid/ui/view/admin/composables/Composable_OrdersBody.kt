package pab.lop.illustrashopandroid.ui.view.admin.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.ui.theme.GreenEnd
import pab.lop.illustrashopandroid.ui.theme.RedPending
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.theme.YellowSent
import pab.lop.illustrashopandroid.utils.*

@Composable
fun Orders(
    customSpacing: Spacing,
    filter: MutableState<String>,
    orderSelected: MutableState<order_response?>,
    isEditOpen: MutableState<Boolean>,
    isAdmin: Boolean
) {
    val ordersSelected = if (isAdmin) allOrders else userOrders
    val finished: String = stringResource(R.string.FINISHED)
    val pending: String = stringResource(R.string.PENDING)
    val sent: String = stringResource(R.string.SENT)

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(customSpacing.mediumSmall)
    ) {

        itemsIndexed(ordersSelected.filter {
            when (filter.value) {
                PENDING -> it.status.uppercase() == PENDING
                SENT -> it.status.uppercase() == SENT
                ENDED -> it.status.uppercase() == ENDED
                else -> true
            }
        }) { _, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = {
                        orderSelected.value = item
                        isEditOpen.value = true
                    }),
                backgroundColor = when (item.status) {
                    PENDING -> RedPending
                    SENT -> YellowSent
                    else -> GreenEnd
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(customSpacing.mediumSmall)

                ) {
                    Text(item.user.username)
                    Text("${item.date_order.month} - ${item.date_order.day}")
                    Text(item.total.toString() + " €")
                    Text(
                        when (item.status.uppercase()) {
                            PENDING -> pending
                            SENT -> sent
                            else -> finished
                        }
                    )
                    Icon(Icons.Filled.Edit, contentDescription = null, tint = Color.Black)

                }
            }
        }
    }
}
