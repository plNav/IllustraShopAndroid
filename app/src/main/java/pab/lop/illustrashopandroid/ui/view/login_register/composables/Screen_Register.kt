package pab.lop.illustrashopandroid.ui.view.login_register.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.utils.ScreenNav
import pab.lop.illustrashopandroid.utils.userSelected


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Register(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel,
    context: Context,
    customSpacing: Spacing,
    isEditionMode: Boolean
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    //Gradients
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


    //Basic Fields
    val email = remember { mutableStateOf(if (isEditionMode) userSelected!!.email else "") }
    val emailChecked = remember { mutableStateOf(isEditionMode) }

    val username = remember { mutableStateOf(if (isEditionMode) userSelected!!.username else "") }
    val usernameChecked = remember { mutableStateOf(isEditionMode) }

    val password1 = remember { mutableStateOf("") }
    val password2 = remember { mutableStateOf("") }
    val passwordChecked = remember { mutableStateOf(false) }
    val passwordVisibility = remember { mutableStateOf(false) }


    //Pay Fields //TODO PAY_METHOD & PAY_NUMBER
    val name = remember { mutableStateOf(if (isEditionMode) userSelected!!.name else "") }
    val nameChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.name.isNotEmpty()
            else false
        )
    }

    val lastName = remember { mutableStateOf(if(isEditionMode) userSelected!!.last_name else "")}
    val lastNameChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.last_name.isNotEmpty()
            else false
        )
    }

    val country = remember { mutableStateOf(if(isEditionMode) userSelected!!.country else "")}
    val countryChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.country.isNotEmpty()
            else false
        )
    }

    val address = remember { mutableStateOf(if(isEditionMode) userSelected!!.address else "")}
    val addressChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.address.isNotEmpty()
            else false
        )
    }

    val postalCode = remember { mutableStateOf(if(isEditionMode) userSelected!!.postal_code else "")}
    val postalCodeChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.postal_code.isNotEmpty()
            else false
        )
    }

    val phone = remember { mutableStateOf(if(isEditionMode) userSelected!!.phone else "")}
    val phoneChecked = remember {
        mutableStateOf(
            if (isEditionMode) userSelected!!.phone.isNotEmpty()
            else false
        )
    }

    //Check by Group
    val basicInfoChecked = remember { mutableStateOf(isEditionMode) }
    val payInfoChecked = remember { mutableStateOf(false) }

    basicInfoChecked.value = (emailChecked.value && usernameChecked.value && passwordChecked.value)
    payInfoChecked.value = (nameChecked.value
            && lastNameChecked.value
            && countryChecked.value
            && addressChecked.value
            && postalCodeChecked.value
            && phoneChecked.value)


    //Booleans categories
    val openBuyInfo = remember { mutableStateOf(false) }
    val showBuyButton = remember { mutableStateOf(false) }

    //Lists to check
    val usernameList = remember { mutableStateOf<List<String>>(listOf()) }
    val emailList = remember { mutableStateOf<List<String>>(listOf()) }

    //Get all emails and usernames one time
    val firstLoad = remember { mutableStateOf(true) }
    if (firstLoad.value) {
        loginRegisterViewModel.getAllEmails { emailList.value = loginRegisterViewModel.emailListResponse }
        loginRegisterViewModel.getAllUsernames { usernameList.value = loginRegisterViewModel.usernameListResponse }
        firstLoad.value = false
    }

    val popUpPasswordOpen = remember {mutableStateOf(false)}
    val passwordValidated = remember { mutableStateOf(false)}
    if(popUpPasswordOpen.value){
        PopUpPassword(
            popUpPasswordOpen = popUpPasswordOpen,
            passwordValidated = passwordValidated
        )
    }

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = customSpacing.large)

    ) {
        item {
            TitleAndBack(
                isEditionMode = isEditionMode,
                customSpacing = customSpacing,
                navController = navController
            )
        }
        item {
            BasicInfo(
                isEditionMode = isEditionMode,
                customSpacing = customSpacing,
                email = email,
                emailChecked = emailChecked,
                username = username,
                usernameChecked = usernameChecked,
                password1 = password1,
                password2 = password2,
                passwordChecked = passwordChecked,
                passwordVisibility = passwordVisibility,
                keyboardController = keyboardController,
                emailList = emailList,
                usernameList = usernameList,
                context = context
            )
        }
        item {
            DropdownButtonPayInfo(
                isEditionMode = isEditionMode,
                verticalGradient = verticalGradient,
                verticalGradientDisabled = verticalGradientDisabled,
                customSpacing = customSpacing,
                openBuyInfo = openBuyInfo
            )
        }
        if (openBuyInfo.value) {
            item {
                PayInfo(
                    isEditionMode = isEditionMode,
                    customSpacing = customSpacing,
                    name = name,
                    nameChecked = nameChecked,
                    lastName = lastName,
                    lastNameChecked = lastNameChecked,
                    country = country,
                    countryChecked = countryChecked,
                    address = address,
                    addressChecked = addressChecked,
                    postalCode = postalCode,
                    postalCodeChecked = postalCodeChecked,
                    phone = phone,
                    phoneChecked = phoneChecked,
                    context = context
                )
            }
        }
        item {
            RegisterButton(
                isEditionMode = isEditionMode,
                verticalGradientDisabled = verticalGradientDisabled,
                verticalGradient = verticalGradient,
                customSpacing = customSpacing,
                openBuyInfo = openBuyInfo,
                basicInfoChecked = basicInfoChecked,
                payInfoChecked = payInfoChecked,
                context = context,
                loginRegisterViewModel = loginRegisterViewModel,
                navController = navController,
                email = email,
                username = username,
                password = password2,
                name = name,
                nameChecked = nameChecked,
                lastName = lastName,
                lastNameChecked = lastNameChecked,
                country = country,
                countryChecked = countryChecked,
                address = address,
                addressChecked = addressChecked,
                postalCode = postalCode,
                postalCodeChecked = postalCodeChecked,
                phone = phone,
                phoneChecked = phoneChecked,
                popUpPasswordOpen = popUpPasswordOpen,
                passwordValidated = passwordValidated
            )
        }
    }
}

@Composable
fun PopUpPassword(popUpPasswordOpen: MutableState<Boolean>, passwordValidated: MutableState<Boolean>) {
    TODO("Not yet implemented")
}


@Composable
private fun TitleAndBack(
    customSpacing: Spacing,
    navController: NavController,
    isEditionMode: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.width(customSpacing.mediumSmall))

        IconButton(
            onClick = {
                if (isEditionMode) navController.navigate(ScreenNav.MainScreen.route)
                else navController.navigate(ScreenNav.LoginScreen.route)
            },
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .align(Alignment.Start)
                .padding(customSpacing.default, customSpacing.mediumMedium, customSpacing.default, customSpacing.default)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                tint = MaterialTheme.colors.primary,
                contentDescription = stringResource(R.string.Back)
            )
        }

        Text(
            text = if (isEditionMode) stringResource(R.string.edit_personal_info) else stringResource(R.string.register),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun DropdownButtonPayInfo(
    verticalGradientDisabled: Brush,
    verticalGradient: Brush,
    customSpacing: Spacing,
    openBuyInfo: MutableState<Boolean>,
    isEditionMode: Boolean
) {

    Card(
        modifier = Modifier
            .padding(customSpacing.mediumMedium)
            .clip(RoundedCornerShape(4.dp))
            .clickable { openBuyInfo.value = !openBuyInfo.value }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(if (openBuyInfo.value) verticalGradient else verticalGradientDisabled)
        ) {
            Text(
                text =
                if(isEditionMode){
                    if(openBuyInfo.value) stringResource(R.string.edit_pay_info_now)
                    else stringResource(R.string.edit_pay_info_later)
                }else {
                    if (openBuyInfo.value) stringResource(R.string.show_pay_info_now)
                    else stringResource(R.string.show_pay_info_later)
                },
                modifier = Modifier
                    .padding(customSpacing.mediumLarge, customSpacing.default, customSpacing.mediumLarge, customSpacing.default),
                color = Color.White
            )

            IconButton(
                modifier = Modifier.padding(
                    customSpacing.default,
                    customSpacing.default,
                    customSpacing.small,
                    customSpacing.default
                ),
                onClick = { openBuyInfo.value = !openBuyInfo.value }
            ) {
                Icon(
                    imageVector = if (openBuyInfo.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}


