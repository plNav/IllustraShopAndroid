package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.utils.URL_HEAD_IMAGES
import pab.lop.illustrashopandroid.utils.productSelected
import pab.lop.illustrashopandroid.utils.regexSpecialChars
import pablo_lonav.android.utils.ScreenNav
import java.nio.file.Files.delete


@OptIn(ExperimentalComposeUiApi::class, androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun Edit_Product(
    navController: NavController,
    adminViewModel: AdminViewModel,
    context: Context,
    customSpacing: Spacing
) {

    val buttonOK = remember { mutableStateOf(false) }
    val chooseFamiliesOpen = remember { mutableStateOf(false) }
    val loadProductsFamily = remember { mutableStateOf(false) }
    val startLoading = remember { mutableStateOf(false) }
    val popUpNewFamily = remember { mutableStateOf(false) }
    val isDeleted = remember { mutableStateOf(false) }
    var products: List<product_stock_response>

    val nameVerified = remember { mutableStateOf(false) }
    val customName = remember { mutableStateOf(productSelected!!.name) }

    val customPrice = remember { mutableStateOf(productSelected!!.price) }
    val customPriceVerified = remember { mutableStateOf(false) }

    val customAmount = remember { mutableStateOf(productSelected!!.stock) }
    val customAmountVerified = remember { mutableStateOf(false) }

    val familiesToAssign = remember { mutableStateOf<List<String>>(productSelected!!.families) }
    val familyNames = remember { mutableStateOf<List<String>>(listOf()) }
    val verificationOpen = remember { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
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

    if (!startLoading.value) {
        startLoading.value = true
        adminViewModel.getProducts {
            products = adminViewModel.productListResponse
            Logger.d("Products => $products")
            loadProductsFamily.value = true
        }
    }

    if (verificationOpen.value) {
        PopUp_Verification(
            navController = navController,
            verificationOpen = verificationOpen,
            verticalGradient = verticalGradient,
            verticalGradientIncomplete = verticalGradientIncomplete,
            customSpacing = customSpacing,
            isEditionMode = true,
            isDelete = isDeleted.value
        )
    }



    if (chooseFamiliesOpen.value) {
        PopUp_Choose_Family(
            adminViewModel = adminViewModel,
            applicationContext = context,
            navController = navController,
            scope = scope,
            chooseFamiliesOpen = chooseFamiliesOpen,
            customSpacing = customSpacing,
            verticalGradient = verticalGradient,
            verticalGradientDisabled = verticalGradientDisabled,
            familyNames = familyNames,
            familiesToAssign = familiesToAssign
        )
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier.background(verticalGradient),
                backgroundColor = Color.Transparent,
                title = {
                    Text(
                        text = stringResource(R.string.edit_product).uppercase(),
                        modifier = Modifier
                            .padding(30.dp, 0.dp, 0.dp, 0.dp),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(ScreenNav.Admin_Screen.route)
                        }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                }
            )
        }
    ) {

        //CONTENT
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(customSpacing.mediumSmall)
        ) {


            item() {

                /************ NAME ************/
                OutlinedTextField(
                    value = customName.value,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = if (nameVerified.value) ImeAction.Next else ImeAction.None
                    ),
                    onValueChange = {
                        it.also {
                            customName.value = it
                            when {
                                it.contains(regexSpecialChars) -> {
                                    nameVerified.value = false
                                    Toast.makeText(context, "Special characters not allowed", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                else -> {
                                    nameVerified.value = true
                                }
                            }
                        }
                    },
                    singleLine = true,
                    label = {
                        Text(
                            text = stringResource(R.string.name),
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.name),
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    trailingIcon = {
                        val image = Icons.Filled.EditNote
                        Icon(
                            imageVector = image,
                            stringResource(R.string.name)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (nameVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedBorderColor = if (nameVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        focusedLabelColor = if (nameVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedLabelColor = if (nameVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        cursorColor = if (nameVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(customSpacing.mediumMedium))

                /************ PRICE ************/
                OutlinedTextField(
                    value = customPrice.value.toString(),
                    onValueChange = {
                        it.also {
                            try {
                                customPrice.value = it.toString().toFloat()
                                customPriceVerified.value = true
                            } catch (e: Exception) {
                                customPriceVerified.value = false
                                Toast.makeText(context, "Not a number", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    label = {
                        Text(
                            text = stringResource(R.string.price),
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.price),
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    trailingIcon = {
                        val image = Icons.Filled.Euro
                        Icon(
                            imageVector = image,
                            contentDescription = stringResource(R.string.price)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (customPriceVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedBorderColor = if (customPriceVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        focusedLabelColor = if (customPriceVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedLabelColor = if (customPriceVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        cursorColor = if (customPriceVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer( modifier = Modifier.height(customSpacing.mediumMedium))

                /************ STOCK ************/
                OutlinedTextField(
                    value = customAmount.value?.toInt().toString(),
                    onValueChange = {
                        it.also {
                            try {
                                customAmount.value = it.toInt().toFloat()
                                customAmountVerified.value = true
                            } catch (e: Exception) {
                                customAmountVerified.value = false
                                Toast.makeText(context, "Not a number", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        autoCorrect = false,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            popUpNewFamily.value = true
                        }),
                    singleLine = true,
                    label = {
                        Text(
                            text = stringResource(R.string.amount),
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.amount),
                            style = TextStyle(
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    },
                    trailingIcon = {
                        val image = Icons.Filled.Inventory
                        Icon(
                            imageVector = image,
                            stringResource(R.string.amount)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = if (customAmountVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedBorderColor = if (customAmountVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        focusedLabelColor = if (customAmountVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                        unfocusedLabelColor = if (customAmountVerified.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                        cursorColor = if (customAmountVerified.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(customSpacing.mediumMedium))

                /************ FAMILIES ************/
                Row(Modifier.fillMaxWidth()) {

                    /************ CHOOSE ************/
                    Text(
                        text = stringResource(R.string.choose_families),
                        textAlign = TextAlign.Center,
                        style = typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = if (familiesToAssign.value.isNullOrEmpty()) verticalGradientIncomplete else verticalGradient)
                            .padding(12.dp)
                            .clickable(onClick = {
                                adminViewModel.getFamilyNames {
                                    familyNames.value = adminViewModel.familyNameListResponse as MutableList<String>
                                    Logger.wtf("Families => ${adminViewModel.familyNameListResponse}")
                                    chooseFamiliesOpen.value = true
                                }
                            })
                    )
                }

                Spacer(modifier = Modifier.height(customSpacing.mediumSmall))



                Spacer(modifier = Modifier.height(customSpacing.small))

                /************ IMAGE ************/
                Card(
                    backgroundColor = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable(onClick = {
                            Toast
                                .makeText(context, "Image can't be modified", Toast.LENGTH_SHORT)
                                .show()
                        })
                ) {
                    AsyncImage(
                        model = "$URL_HEAD_IMAGES${productSelected!!.image}",
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.loading_image),
                        contentScale = ContentScale.Fit,
                        //   modifier = Modifier.fillMaxSize(0.8f)
                    )
                }


                /************ UPDATE ************/
                Text(
                    text = (stringResource(R.string.update)).uppercase(),
                    textAlign = TextAlign.Center,
                    style = typography.body1.copy(color = Color.White),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (nameVerified.value
                                && customAmountVerified.value
                                && customPriceVerified.value
                                && !familiesToAssign.value.isNullOrEmpty()
                            )
                                verticalGradient
                            else verticalGradientDisabled
                        )
                        .padding(12.dp)
                        .clickable(onClick = {
                            if (nameVerified.value
                                && customAmountVerified.value
                                && customPriceVerified.value
                                && !familiesToAssign.value.isNullOrEmpty()
                            ) {
                                val newProduct = product_stock_response(
                                    _id = productSelected!!._id,
                                    name = customName.value,
                                    image = productSelected!!.image,
                                    price = customPrice.value,
                                    stock = customAmount.value,
                                    families = familiesToAssign.value as MutableList<String>,
                                    likes = productSelected!!.likes,
                                    wishlists = productSelected!!.wishlists,
                                    sales = productSelected!!.sales
                                )

                                adminViewModel.updateProductStock(newProduct = newProduct, oldProductId = productSelected!!._id) {
                                    Logger.i("Success update Product")
                                    verificationOpen.value = true
                                    buttonOK.value = true;
                                }


                            } else Toast
                                .makeText(context, "Incorrect Fields", Toast.LENGTH_SHORT)
                                .show()

                        })


                )


                Spacer(modifier = Modifier.height(customSpacing.small))


                /************ DELETE ************/
                Text(
                    text = (stringResource(R.string.delete)).uppercase(),
                    textAlign = TextAlign.Center,
                    style = typography.body1.copy(color = Color.White),
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(verticalGradientIncomplete)
                        .padding(12.dp)
                        .clickable(onClick = {
                            adminViewModel.deleteProductStock(oldProductId = productSelected!!._id) {
                                Logger.i("Success update Product")
                                isDeleted.value = true
                                verificationOpen.value = true
                                buttonOK.value = true;
                            }
                        })
                )
            }
        }
    }
}










