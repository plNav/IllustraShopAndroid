package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.utils.familyNameList
import pab.lop.illustrashopandroid.utils.ScreenNav
import pab.lop.illustrashopandroid.utils.allOrders

@Composable
fun Admin_Screen(
    navController: NavController,
    adminViewModel: AdminViewModel,
    context: Context,
    customSpacing: Spacing,
){
    val createFamilyOpen = remember { mutableStateOf(false) }
    val selectionProductOpen = remember { mutableStateOf(false) }
    val selectionFamilyOpen = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )
    val verticalGradientDisabled = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onError, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )

    val verticalGradientIncomplete = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.onSecondary, Color.DarkGray),
        startY = 0f,
        endY = 100f
    )

    if (createFamilyOpen.value) {
        PopUp_Create_Family(
            adminViewModel = adminViewModel,
            applicationContext = context,
            navController = navController,
            scope = scope,
            createFamilyOpen = createFamilyOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradiendDisabled = verticalGradientDisabled

        )
    }

    if(selectionProductOpen.value){
        PopUp_Selection(
            applicationContext = context,
            navController = navController,
            scope = scope,
            adminViewModel = adminViewModel,
            selectionOpen = selectionProductOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            isProduct = true,
            verticalGradientIncomplete = verticalGradientIncomplete

        )
    }

    if(selectionFamilyOpen.value){
        PopUp_Selection(
            applicationContext = context,
            navController = navController,
            scope = scope,
            adminViewModel = adminViewModel,
            selectionOpen = selectionProductOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            isProduct = false,
            verticalGradientIncomplete = verticalGradientIncomplete
        )
    }


    Column() {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = verticalGradient)
        ) {

            /************ BACK ************/
            IconButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .fillMaxWidth(0.15f)
                    .background(Color.Transparent),
                onClick = { navController.navigate(ScreenNav.MainScreen.route) },
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = stringResource(R.string.Back)
                )
            }


            /************ TITLE ************/
            Text(
                text = stringResource(R.string.menu_admin).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Transparent)
                    .padding(0.dp, customSpacing.mediumSmall, customSpacing.superLarge, customSpacing.mediumSmall)
                    .clickable(onClick = { })
            )


        }

        Spacer(
            modifier = Modifier.height(
                customSpacing.mediumSmall
            )
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(customSpacing.mediumMedium)
        ) {


            /************ CREATE PRODUCT STOCK ************/
            Text(
                text = stringResource(R.string.new_product),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { navController.navigate(ScreenNav.ImageUploadScreen.route) })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )

            /************ EDIT/DELETE PRODUCT STOCK ************/
            Text(
                text = (stringResource(R.string.edit_product)).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { selectionProductOpen.value = true })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )

            /************ CREATE FAMILY ************/
            Text(
                text = (stringResource(R.string.new_family)).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                        adminViewModel.getFamilyNames {
                            familyNameList = adminViewModel.familyNameListResponse as MutableList<String>
                            Logger.wtf("Families => ${adminViewModel.familyNameListResponse}")

                            // loadProductsFamily.value = true
                            createFamilyOpen.value = true

                        }
                    })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )

            /************ EDIT/DELETE FAMILY ************/
            Text(
                text = (stringResource(R.string.edit_family)).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { selectionFamilyOpen.value = true })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )

            /************ REQUESTS ************/
            Text(
                text = (stringResource(R.string.requests)).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {
                        adminViewModel.getOrders(){
                            allOrders = adminViewModel.allOrdersResponse as MutableList<order_response>
                            navController.navigate(ScreenNav.OrderScreen.withArgs(true))
                        }
                    })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )

            /************ ANALYTICS ************/
            Text(
                text = (stringResource(R.string.analytics)).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = { navController.navigate(ScreenNav.AnalyticsScreen.route) })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )



        }
    }
}
