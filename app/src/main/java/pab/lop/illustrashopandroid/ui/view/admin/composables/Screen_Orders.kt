package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.ui.theme.*
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.utils.*

@Composable
fun OrderStart(
    navController: NavController,
    adminViewModel: AdminViewModel,
    context: Context,
    customSpacing: Spacing,
    isAdmin: Boolean
) {

    val isEditOpen = remember { mutableStateOf(false) }
    val orderSelected = remember { mutableStateOf<order_response?>(null) }
    val filter = remember { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )

    if (isEditOpen.value) {
        PopUp_EditOrder(
            isEditOpen = isEditOpen,
            orderSelected = orderSelected,
            adminViewModel = adminViewModel,
            verticalGradient = verticalGradient,
            navController = navController,
            customSpacing = customSpacing,
            isAdmin = isAdmin
        )
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            OrderTopBar(
                verticalGradient = verticalGradient,
                navController = navController
            )
        }
    ) {

        Column {
            Filters(
                filter = filter,
                customSpacing = customSpacing
            )
            Orders(
                customSpacing = customSpacing,
                filter = filter,
                orderSelected = orderSelected,
                isEditOpen = isEditOpen,
                isAdmin = isAdmin
            )
        }
    }
}



@Composable
private fun OrderTopBar(verticalGradient: Brush, navController: NavController) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.background(verticalGradient),
        backgroundColor = Color.Transparent,
        title = {
            Text(
                text = stringResource(R.string.orders).uppercase(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
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
        }
    )
}



