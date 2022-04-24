package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.utils.familyNameList
import pab.lop.illustrashopandroid.utils.familySelected
import pab.lop.illustrashopandroid.utils.productSelected
import pab.lop.illustrashopandroid.utils.ScreenNav

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopUp_Selection(
    applicationContext: Context,
    navController: NavController,
    scope: CoroutineScope,
    adminViewModel: AdminViewModel,
    selectionOpen: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradient: Brush,
    verticalGradientDisabled: Brush,
    isProduct: Boolean,
    verticalGradientIncomplete: Brush
) {
    val families = remember { mutableStateOf<List<family_response>>(listOf()) }
    val products = remember { mutableStateOf<List<product_stock_response>>(listOf()) }
    val editFamilyOpen = remember { mutableStateOf(false) }

    if (editFamilyOpen.value) {
        PopUp_Edit_Family(
            applicationContext = applicationContext,
            navController = navController,
            scope = scope,
            adminViewModel = adminViewModel,
            createFamilyOpen = editFamilyOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradiendDisabled = verticalGradientDisabled,
            verticalGradientIncomplete = verticalGradientIncomplete,
        )
    }


    Logger.wtf("in popup ===>> $familyNameList")

    if (isProduct) {
        adminViewModel.getProducts {
            Logger.i("Get Products On PopUp_Selection")
            products.value = adminViewModel.productListResponse
        }
    } else {
        adminViewModel.getFamilies {
            Logger.i("Get Families ON PopUp_Selection")
            families.value = adminViewModel.familyListResponse
        }
    }

    Dialog(onDismissRequest = {
        selectionOpen.value = false
        navController.navigate(ScreenNav.AdminScreen.route)
    }) {
        Surface(
            modifier = Modifier
                .padding(customSpacing.small)
                .wrapContentHeight(),

            shape = RoundedCornerShape(5.dp),
            color = Color.White
        ) {
            Column(modifier = Modifier.wrapContentHeight()) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = verticalGradient)
                ) {

                    /************ TITLE ************/
                    Text(
                        text = if(isProduct) (stringResource(R.string.edit_product)).uppercase() else (stringResource(R.string.edit_family).uppercase()),
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
                        onClick = {
                            selectionOpen.value = false
                            navController.navigate(ScreenNav.AdminScreen.route)
                        },
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

                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = customSpacing.mediumSmall)
                        .fillMaxHeight(0.8f)

                ) {


                    if (isProduct) {
                        items(products.value) { product ->
                            Column() {
                                Text(
                                    text = product.name,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1.copy(color = Color.White),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(brush = verticalGradientDisabled)
                                        .padding(12.dp)
                                        .clickable(onClick = {
                                            /*adminViewModel.getProducts {
                                                Logger.i("Get Products Ok")

                                            }*/
                                            productSelected = product
                                            navController.navigate(ScreenNav.ProductEditScreen.route)

                                        })
                                )
                                Spacer(
                                    modifier = Modifier.height(
                                        customSpacing.mediumSmall
                                    )
                                )
                            }
                        }

                    } else {
                        items(families.value) { family ->
                            Column() {
                                Text(
                                    text = family.name,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.body1.copy(color = Color.White),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(brush = verticalGradientDisabled)
                                        .padding(12.dp)
                                        .clickable(onClick = {
                                            adminViewModel.getFamilyNames {
                                                Logger.wtf("Families => ${adminViewModel.familyNameListResponse}")
                                                familySelected = family
                                                familyNameList = adminViewModel.familyNameListResponse as MutableList<String>
                                                editFamilyOpen.value = true
                                            }
                                        })
                                )
                                Spacer(
                                    modifier = Modifier.height(
                                        customSpacing.mediumSmall
                                    )
                                )
                            }
                        }
                    }
                }
                Text("") // ÑAPAS
            }
        }
    }
}
