package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
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
import androidx.compose.material.icons.filled.Close
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
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pablo_lonav.android.utils.ScreenNav

@Composable
fun Admin_Screen(
    navController: NavController,
    adminViewModel: AdminViewModel,
    context: Context,
    customSpacing: Spacing,
){

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
                onClick = { navController.navigate(ScreenNav.LoginScreen.route) },
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
                    .padding(0.dp,customSpacing.mediumSmall, customSpacing.superLarge,customSpacing.mediumSmall)
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
                    .clickable(onClick = {navController.navigate(ScreenNav.Image_Upload.route) })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.extraLarge
                )
            )

            /************ EDIT/DELETE PRODUCT STOCK ************/
            Text(
                text = stringResource(R.string.new_product),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {navController.navigate(ScreenNav.Image_Upload.route) })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.extraLarge
                )
            )

            /************ CREATE FAMILY ************/
            Text(
                text = stringResource(R.string.new_product),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {navController.navigate(ScreenNav.Image_Upload.route) })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.extraLarge
                )
            )

            /************ EDIT/DELETE FAMILY ************/
            Text(
                text = stringResource(R.string.new_product),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(brush = verticalGradientDisabled)
                    .padding(12.dp)
                    .clickable(onClick = {navController.navigate(ScreenNav.Image_Upload.route) })
            )

            Spacer(
                modifier = Modifier.height(
                    customSpacing.large
                )
            )

        }
    }
}
