package pab.lop.illustrashopandroid.ui.view.admin.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.utils.ScreenNav

@Composable
fun PopUp_Verification(
    navController: NavController,
    verificationOpen: MutableState<Boolean>,
    verticalGradient: Brush,
    verticalGradientIncomplete: Brush,
    customSpacing: Spacing,
    isEditionMode: Boolean,
    isDelete : Boolean

) {

    Dialog(
        onDismissRequest = {
            verificationOpen.value = false
            navController.navigate(ScreenNav.AdminScreen.route)
        }) {
        Surface(
            modifier = Modifier.padding(customSpacing.small),
            shape = RoundedCornerShape(5.dp),
            color = Color.White
        ) {

            Column() {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {


                    /************ TITLE ************/
                    Text(
                        text = if(isEditionMode){
                            if(isDelete) stringResource(R.string.delete_completed).uppercase()
                            else stringResource(R.string.update_complete)
                        } else stringResource(R.string.upload_complete).uppercase(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent)
                            .padding(12.dp)
                            .clickable(onClick = { })
                    )

                    /************ CLOSE ************/
                    IconButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Transparent),
                        onClick = { verificationOpen.value = false },
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            tint = MaterialTheme.colors.onSecondary,
                            contentDescription = stringResource(R.string.Close)
                        )
                    }
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
                        .fillMaxWidth()
                        .padding(customSpacing.mediumMedium)
                ) {

                   /* Text(text = stringResource(R.string.what_now))

                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.mediumMedium
                        )
                    )*/

/*
                    *//************ NEW UPLOAD ************//*
                    Text(
                        text = stringResource(R.string.new_product),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradient)
                            .padding(12.dp)
                            .clickable(onClick = { navController.navigate(ScreenNav.Image_Upload.route) })
                    )

                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.mediumSmall
                        )
                    )*/

                    /************ RETURN ************/
                    Text(
                        text = stringResource(R.string.Back),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradientIncomplete)
                            .padding(12.dp)
                            .clickable(onClick = { navController.navigate(ScreenNav.AdminScreen.route) })
                    )
                    Text("")
                }
            }
        }
    }
}