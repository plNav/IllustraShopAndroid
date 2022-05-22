package pab.lop.illustrashopandroid.ui.view.main.composables

import android.content.Context
import android.graphics.Paint
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.ui.theme.Shapes
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.ui.view.main.MainViewModel
import pab.lop.illustrashopandroid.utils.*

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
    adminViewModel: AdminViewModel,
    loginRegisterViewModel: LoginRegisterViewModel
) {
    if(userSelected == null) userSelected = userDefaultNoAuth

    /*** USER INFO ***/
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .background(verticalGradientDisabled)
            .padding(vertical = customSpacing.mediumLarge, horizontal = customSpacing.mediumMedium)
    ){

        Card(
            border = BorderStroke(2.dp, Color.DarkGray),
            modifier = Modifier.background(Color.Transparent),
            shape = RoundedCornerShape(15.dp),
            elevation = 20.dp
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
            ){
                Text(

                    text = stringResource(R.string.logged_as),
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(
                            start = customSpacing.mediumMedium,
                            top = customSpacing.mediumMedium,
                            end = customSpacing.small,
                            bottom = customSpacing.default
                        )
                        .align(Alignment.Start),
                    fontSize = 10.sp,
                    textAlign = TextAlign.Left,


                    )
                Text(
                    text = if (userSelected == userDefaultNoAuth) stringResource(R.string.not_logged) else userSelected!!.username,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxWidth()
                        .padding(
                            start = customSpacing.small,
                            top = customSpacing.small,
                            end = customSpacing.mediumMedium,
                            bottom = customSpacing.mediumLarge
                        )
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.End

                )
            }
        }
    }
    Spacer(modifier = Modifier.height(customSpacing.small))
    Divider(color = Color.DarkGray, thickness = 5.dp)
    Spacer(modifier = Modifier.height(customSpacing.small))
    Divider(color = Color.DarkGray, thickness = 3.dp)
    Spacer(modifier = Modifier.height(customSpacing.small))
    Divider(color = Color.DarkGray, thickness = 2.dp)
    Spacer(modifier = Modifier.height(customSpacing.small))
    Divider(color = Color.DarkGray, thickness = 1.dp)

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.65f)
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Divider(color = Color.DarkGray, thickness = 1.dp)

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
            Divider(color = Color.DarkGray, thickness = 1.dp)

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
                        mainViewModel.getAllProductStock(userSelected!!.wishlist){
                            wishlistProducts = mainViewModel.productListResponse
                            navController.navigate(ScreenNav.WishScreen.route)
                        }
                    })
            )

            Divider(color = Color.DarkGray, thickness = 1.dp)

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
                        if (userSelected!!.rol.uppercase() == "ADMIN") {
                            adminViewModel.getOrders() {
                                allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                                navController.navigate(ScreenNav.OrderScreen.withArgs(true))
                            }
                        } else {
                            adminViewModel.getUserOrders(userId = userSelected!!._id) {
                                allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                                navController.navigate(ScreenNav.OrderScreen.withArgs(false))
                            }
                        }
                    })
            )
        }


        /*** ADMIN SETTINGS IF USER ROL == ADMIN ***/
        if (userSelected!!.rol.uppercase() == "ADMIN") {
            Divider(color = Color.DarkGray, thickness = 1.dp)
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

        Divider(color = Color.DarkGray, thickness = 1.dp)

        /*** CLOSE SESSION - INIT SESSION IF NO AUTH USER ***/
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = verticalGradientDisabled)
                .padding(12.dp)
                .clickable(onClick = {
                    if(userSelected!!.google){
                        loginRegisterViewModel._user = MutableStateFlow(null)
                        loginRegisterViewModel.user = loginRegisterViewModel._user
                    }
                    userSelected = null
                    navController.navigate(ScreenNav.LoginScreen.route)

                }),
            text =
            if (userSelected == userDefaultNoAuth) stringResource(R.string.login)
            else stringResource(R.string.logout),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1.copy(color = Color.White),
        )
        Divider(color = Color.DarkGray, thickness = 1.dp)

    }

}