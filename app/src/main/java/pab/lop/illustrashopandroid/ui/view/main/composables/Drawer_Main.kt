package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.ScreenNav
import pab.lop.illustrashopandroid.utils.allOrders
import pab.lop.illustrashopandroid.utils.userDefaultNoAuth
import pab.lop.illustrashopandroid.utils.userSelected

@Composable
fun MainDrawer(
    navController: NavController,
    context: Context,
    verticalGradient: Brush,
    scaffoldState: ScaffoldState,
    customSpacing: Spacing,
    scope: CoroutineScope,
    mainViewModel: MainViewModel,
    verticalGradientDisabled: Brush,
    adminViewModel: AdminViewModel
) {
    if(userSelected == null) userSelected = userDefaultNoAuth

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.65f)
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        /*** USER INFO ***/
        Card(Modifier.fillMaxWidth()) {
            //TODO DEJARLO BONICO
            Text(if (userSelected == userDefaultNoAuth) stringResource(R.string.not_logged) else userSelected!!.username)
        }

        /*** EDIT PERSONAL INFO ***/
        Text(
            text =
            if (userSelected == userDefaultNoAuth) stringResource(R.string.register)
            else stringResource(R.string.edit_personal_info),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = {
                    if (userSelected == userDefaultNoAuth) navController.navigate(ScreenNav.RegisterScreen.withArgs(false))
                    else navController.navigate(ScreenNav.RegisterScreen.withArgs(true))
                })
        )


        if (userSelected!! != userDefaultNoAuth){
            /*** WISHLIST ***/
            Text(
                text = stringResource(R.string.whislist),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                        //TODO WISHLIST
                        navController.navigate(ScreenNav.ImageUploadScreen.route)
                    })
            )


            /*** DELIVERS ***/
            Text(
                text = stringResource(R.string.orders),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                        if(userSelected!!.rol.uppercase() == "ADMIN"){
                            adminViewModel.getOrders(){
                                allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                                navController.navigate(ScreenNav.OrderScreen.withArgs(true))
                            }
                        }
                        else{
                            //TODO ONLY SHOW ALLORDERS FROM USER X1
                            adminViewModel.getOrders(){
                                allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                                navController.navigate(ScreenNav.OrderScreen.withArgs(false))
                            }
                        }
                    })
            )
        }


        /*** ADMIN SETTINGS IF USER ROL == ADMIN ***/
        if (userSelected!!.rol.uppercase() == "ADMIN") {
            Text(
                text = stringResource(R.string.menu_admin),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { navController.navigate(ScreenNav.AdminScreen.route) })
            )
        }


        /*** CLOSE SESSION - INIT SESSION IF NO AUTH USER ***/
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = {
                    userSelected = null
                    navController.navigate(ScreenNav.LoginScreen.route)
                }),
            text =
            if (userSelected == userDefaultNoAuth) stringResource(R.string.login)
            else stringResource(R.string.logout),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
        )
    }
}