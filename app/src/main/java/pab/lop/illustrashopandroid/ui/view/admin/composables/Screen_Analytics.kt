package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.utils.ScreenNav

@Composable
fun Analytics(
    navController: NavController,
    adminViewModel: AdminViewModel,
    context: Context,
    customSpacing: Spacing,
) {

    val buys = stringResource(R.string.buys)
    val users = stringResource(R.string.users)
    val total = stringResource(R.string.total)



    val firstOpen = remember { mutableStateOf(true) }
    val scaffoldState = rememberScaffoldState()

    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )

    if (firstOpen.value) {
        adminViewModel.getAnalytics() {
            firstOpen.value = false
        }
    } else {


        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AnalyticsTopBar(
                    verticalGradient = verticalGradient,
                    navController = navController
                )
            }
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(customSpacing.mediumSmall),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                item {
                    CustomCard(
                        name = users,
                        value = "${adminViewModel.analyticsResponse!!.users}"
                    )
                }

                item {
                    CustomCard(
                       name = buys,
                       value = "${adminViewModel.analyticsResponse!!.buys}"
                    )
                }

                item {
                    CustomCard(
                        name = total,
                        value = "${adminViewModel.analyticsResponse!!.total} €"
                    )
                }
            }
        }
    }
}

@Composable
fun CustomCard(name: String, value: String) {
    Card(
        backgroundColor = Color.DarkGray,
        modifier = Modifier.fillMaxWidth()
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = name, modifier = Modifier.fillMaxWidth(0.5f), color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = value, modifier = Modifier.fillMaxWidth(0.5f), color = Color.White, fontWeight = FontWeight.Bold)
        }
    }

}


@Composable
private fun AnalyticsTopBar(verticalGradient: Brush, navController: NavController) {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.background(verticalGradient),
        backgroundColor = Color.Transparent,
        title = {
            Text(
                text = stringResource(R.string.analytics).uppercase(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(ScreenNav.AdminScreen.route)
                }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        }
    )
}


