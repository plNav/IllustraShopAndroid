package pab.lop.illustrashopandroid.ui.view.login_register.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.user.user_request
import pab.lop.illustrashopandroid.ui.theme.*
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.utils.getSHA256
import pab.lop.illustrashopandroid.utils.regexSpecialChars
import pab.lop.illustrashopandroid.utils.userSelected
import pablo_lonav.android.utils.ScreenNav


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Register(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel,
    context: Context,
    customSpacing: Spacing
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
    val email = remember { mutableStateOf("") }
    val emailChecked = remember { mutableStateOf(false) }

    val username = remember { mutableStateOf("") }
    val usernameChecked = remember { mutableStateOf(false) }

    val password1 = remember { mutableStateOf("") }
    val password2 = remember { mutableStateOf("") }
    val passwordChecked = remember { mutableStateOf(false) }


    //Pay Fields


    //Check by Group
    val basicInfoChecked = remember { mutableStateOf(false) }
    val payInfoChecked = remember { mutableStateOf(true) }

    basicInfoChecked.value = (emailChecked.value && usernameChecked.value && passwordChecked.value)


    val passwordVisibility = remember { mutableStateOf(false) }

    val emptyError = stringResource(R.string.emptyError)

    //Booleans categories
    val openBuyInfo = remember { mutableStateOf(false) }
    val showBuyButton = remember { mutableStateOf(false) }

    //Lists to check
    val usernameList = remember { mutableStateOf<List<String>>(listOf()) }
    val emailList = remember { mutableStateOf<List<String>>(listOf()) }

    val firstLoad = remember { mutableStateOf(true) }
    if (firstLoad.value) {
        loginRegisterViewModel.getAllEmails { emailList.value = loginRegisterViewModel.emailListResponse }
        loginRegisterViewModel.getAllUsernames { usernameList.value = loginRegisterViewModel.usernameListResponse }
        firstLoad.value = false
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
                customSpacing = customSpacing,
                navController = navController
            )
        }
        item {
            BasicInfo(
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
                verticalGradient = verticalGradient,
                verticalGradientDisabled = verticalGradientDisabled,
                customSpacing = customSpacing,
                openBuyInfo = openBuyInfo
            )
        }
        if (openBuyInfo.value) {
            item {
                PayInfo(
                    customSpacing = customSpacing,
                    email = email,
                    password = password1,
                    keyboardController = keyboardController,
                    passwordVisibility = passwordVisibility
                )
            }
        }
        item {
            RegisterButton(
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
                password = password1
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun BasicInfo(
    customSpacing: Spacing,
    email: MutableState<String>,
    keyboardController: SoftwareKeyboardController?,
    passwordVisibility: MutableState<Boolean>,
    password1: MutableState<String>,
    password2: MutableState<String>,
    passwordChecked: MutableState<Boolean>,
    usernameChecked: MutableState<Boolean>,
    username: MutableState<String>,
    emailChecked: MutableState<Boolean>,
    emailList: MutableState<List<String>>,
    usernameList: MutableState<List<String>>,
    context: Context
) {

    val alreadyUseError = context.getString(R.string.already_use_error)
    val noEmailError = context.getString(R.string.noEmailError)
    val specialCharError = context.getString(R.string.specialCharError)
    val passwordMatchError = context.getString(R.string.passwordMatchError)
    val emptyFieldError = context.getString(R.string.emptyFieldError)
    val fieldOK = context.getString(R.string.fieldOK)


    val spaceBetweenFields = customSpacing.small

    val fieldModifier = Modifier
        .fillMaxWidth()
        .height(customSpacing.extraLarge + customSpacing.small)
        .padding(customSpacing.default)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = customSpacing.mediumMedium)

    ) {

        Spacer(modifier = Modifier.height(customSpacing.mediumMedium))

        /************ EMAIL ************/
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                it.also {
                    email.value = it
                    when {
                        emailList.value.contains(it) -> emailChecked.value = false
                        it.isEmpty() -> emailChecked.value = false
                        android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches() -> emailChecked.value = true
                        else -> emailChecked.value = false
                    }
                }
            },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.email),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.email),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Email
                IconButton(
                    onClick = {
                        when {
                            emailList.value.contains(email.value) -> Toast.makeText(context, alreadyUseError, Toast.LENGTH_SHORT).show()
                            email.value.isEmpty() -> Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                            android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches() -> Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(context, noEmailError, Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (emailChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (emailChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (emailChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (emailChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ USERNAME ************/
        OutlinedTextField(
            value = username.value,
            onValueChange = {
                it.also {
                    username.value = it
                    when {
                        usernameList.value.contains(it) -> usernameChecked.value = false
                        it.contains(regexSpecialChars) -> usernameChecked.value = false
                        it.isEmpty() -> usernameChecked.value = false
                        else -> usernameChecked.value = true
                    }
                }
            },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.username),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.username),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Person
                IconButton(
                    onClick = {
                        when {
                            usernameList.value.contains(username.value) -> Toast.makeText(context, alreadyUseError, Toast.LENGTH_SHORT).show()
                            username.value.isEmpty() -> Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                            username.value.contains(regexSpecialChars) -> Toast.makeText(context, specialCharError, Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (usernameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (usernameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (usernameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (usernameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (usernameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ PASSWORD ************/
        OutlinedTextField(
            value = password1.value,
            onValueChange = { it.also { password1.value = it } },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.Password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.Password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    ),
                )
            },
            visualTransformation =
            if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            trailingIcon = {
                val image =
                    if (passwordVisibility.value) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                IconButton(
                    onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                        when {
                            password1.value.isEmpty() || password2.value.isEmpty() -> Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                            password2.value == password1.value -> Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(context,passwordMatchError, Toast.LENGTH_SHORT).show()
                        }
                    }                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ REPEAT PASSWORD ************/
        OutlinedTextField(
            value = password2.value,
            onValueChange = {
                it.also {
                    password2.value = it
                    passwordChecked.value = (password2.value == password1.value
                            && (password1.value.isNotEmpty() && password2.value.isNotEmpty()))
                }
            },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.repeat_password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.repeat_password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    ),
                )
            },
            visualTransformation =
            if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    //TODO PERFORM ONCLICK VALIDATE
                }),
            trailingIcon = {
                val image =
                    if (passwordVisibility.value) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                IconButton(
                    onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                        when {
                            password1.value.isEmpty() || password2.value.isEmpty() -> Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                            password2.value == password1.value -> Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
                            else -> Toast.makeText(context,passwordMatchError, Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.repeat_password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (passwordChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (passwordChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (passwordChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (passwordChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (passwordChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )


        Spacer(modifier = Modifier.height(customSpacing.small))


    }
}

@Composable
private fun TitleAndBack(
    customSpacing: Spacing,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        //TODO TITLE AND BACK

        Spacer(modifier = Modifier.width(customSpacing.mediumSmall))

        IconButton(
            onClick = { navController.navigate(ScreenNav.LoginScreen.route) },

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
            text = stringResource(R.string.register),
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
    openBuyInfo: MutableState<Boolean>
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
                text = if (openBuyInfo.value) stringResource(R.string.show_pay_info_now) else stringResource(R.string.show_pay_info_later),
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

@Composable
fun RegisterButton(
    verticalGradientDisabled: Brush,
    verticalGradient: Brush,
    customSpacing: Spacing,
    openBuyInfo: MutableState<Boolean>,
    basicInfoChecked: MutableState<Boolean>,
    payInfoChecked: MutableState<Boolean>,
    context: Context,
    loginRegisterViewModel: LoginRegisterViewModel,
    navController: NavController,
    email: MutableState<String>,
    username: MutableState<String>,
    password: MutableState<String>,
) {

    Card(
        modifier = Modifier
            .padding(customSpacing.mediumMedium)
            .clip(RoundedCornerShape(4.dp))
            .clickable {
                if (isValidated(
                        openBuyInfo = openBuyInfo,
                        basicInfoChecked = basicInfoChecked,
                        payInfoChecked = payInfoChecked,
                        option1 = true,
                        option2 = false,
                        withToast = false,
                        context = context
                    ) as Boolean
                ) validateClick(
                    context = context,
                    allFields = openBuyInfo.value,
                    loginRegisterViewModel = loginRegisterViewModel,
                    navController = navController,
                    email = email,
                    username = username,
                    password = password
                )
                else isValidated(
                    openBuyInfo = openBuyInfo,
                    basicInfoChecked = basicInfoChecked,
                    payInfoChecked = payInfoChecked,
                    option1 = true,
                    option2 = false,
                    withToast = true,
                    context = context
                )
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    isValidated(
                        openBuyInfo = openBuyInfo,
                        basicInfoChecked = basicInfoChecked,
                        payInfoChecked = payInfoChecked,
                        option1 = verticalGradient,
                        option2 = verticalGradientDisabled,
                        withToast = false,
                        context = context
                    ) as Brush
                )
        ) {
            Text(
                text = stringResource(R.string.register),
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
                onClick = {
                    if (isValidated(
                            openBuyInfo = openBuyInfo,
                            basicInfoChecked = basicInfoChecked,
                            payInfoChecked = payInfoChecked,
                            option1 = true,
                            option2 = false,
                            withToast = false,
                            context = context
                        ) as Boolean
                    ) validateClick(
                        context = context,
                        allFields = openBuyInfo.value,
                        loginRegisterViewModel = loginRegisterViewModel,
                        navController = navController,
                        email = email,
                        username = username,
                        password = password
                    )
                    else isValidated(
                        openBuyInfo = openBuyInfo,
                        basicInfoChecked = basicInfoChecked,
                        payInfoChecked = payInfoChecked,
                        option1 = true,
                        option2 = false,
                        withToast = true,
                        context = context
                    )
                }
            ) {
                Icon(
                    imageVector = isValidated(
                        openBuyInfo = openBuyInfo,
                        basicInfoChecked = basicInfoChecked,
                        payInfoChecked = payInfoChecked,
                        option1 = Icons.Filled.VerifiedUser,
                        option2 = Icons.Filled.PersonOff,
                        withToast = false,
                        context = context
                    ) as ImageVector,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

fun validateClick(
    context: Context,
    allFields: Boolean,
    loginRegisterViewModel: LoginRegisterViewModel,
    navController: NavController,
    email: MutableState<String>,
    username: MutableState<String>,
    password: MutableState<String>
) {
    val newUser = user_request(
        name = if(!allFields) "" else "",
        last_name = if(!allFields) "" else "",
        username = username.value,
        email = email.value,
        password = getSHA256(password.value),
        rol = "Standard",
        address = if(!allFields) "" else "",
        country = if(!allFields) "" else "",
        postal_code = if(!allFields) "" else "",
        phone = if(!allFields) "" else "",
        pay_method = if(!allFields) "" else "",
        pay_number = if(!allFields) "" else ""
    )

    loginRegisterViewModel.createUser(newUser){
        userSelected = loginRegisterViewModel.currentUserResponse.value
        Toast.makeText(context, context.getString(R.string.register_correct) + "\n" + userSelected!!.username, Toast.LENGTH_SHORT).show()
        navController.navigate(ScreenNav.MainScreen.route)
    }
}

private fun isValidated(
    openBuyInfo: MutableState<Boolean>,
    basicInfoChecked: MutableState<Boolean>,
    payInfoChecked: MutableState<Boolean>,
    option1: Any,
    option2: Any,
    withToast: Boolean,
    context: Context
): Any {

    val errorBasic = context.getString(R.string.errorBasicInfo)
    val errorPay = context.getString(R.string.errorPay1) + "\n" + context.getString(R.string.errorPay2)
    val errorBoth = context.getString(R.string.errorBoth1) + "\n" + context.getString(R.string.errorBoth2)

    if (openBuyInfo.value) {
        return if (basicInfoChecked.value && payInfoChecked.value) option1
        else if (!basicInfoChecked.value && payInfoChecked.value) {
            if (withToast) Toast.makeText(context, errorBasic, Toast.LENGTH_SHORT).show()
            option2
        } else if (basicInfoChecked.value && !payInfoChecked.value) {
            if (withToast) Toast.makeText(context, errorPay, Toast.LENGTH_LONG).show()
            option2
        } else {
            if (withToast) Toast.makeText(context, errorBoth, Toast.LENGTH_LONG).show()
            option2
        }
    } else {
        return if (basicInfoChecked.value) option1
        else {
            if (withToast) Toast.makeText(context, errorBasic, Toast.LENGTH_SHORT).show()
            option2
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun PayInfo(
    customSpacing: Spacing,
    email: MutableState<String>,
    password: MutableState<String>,
    keyboardController: SoftwareKeyboardController?,
    passwordVisibility: MutableState<Boolean>
) {

    val spaceBetweenFields = customSpacing.small

    val fieldModifier = Modifier
        .fillMaxWidth()
        .height(customSpacing.extraLarge + customSpacing.small)
        .padding(customSpacing.default)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = customSpacing.mediumMedium)

    ) {

        /************ EMAIL ************/
        OutlinedTextField(
            value = email.value,
            onValueChange = { it.also { email.value = it } },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.email),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.email),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Email
                Icon(
                    imageVector = image,
                    stringResource(R.string.email)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ PASSWORD ************/
        OutlinedTextField(
            value = password.value,
            onValueChange = { it.also { password.value = it } },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.Password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.Password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    ),
                )
            },
            visualTransformation =
            if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    //TODO PERFORM ONCLICK VALIDATE
                }),
            trailingIcon = {
                val image =
                    if (passwordVisibility.value) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                IconButton(
                    onClick = { passwordVisibility.value = !passwordVisibility.value }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ PASSWORD ************/
        OutlinedTextField(
            value = password.value,
            onValueChange = { it.also { password.value = it } },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.Password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.Password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    ),
                )
            },
            visualTransformation =
            if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    //TODO PERFORM ONCLICK VALIDATE
                }),
            trailingIcon = {
                val image =
                    if (passwordVisibility.value) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                IconButton(
                    onClick = { passwordVisibility.value = !passwordVisibility.value }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ PASSWORD ************/
        OutlinedTextField(
            value = password.value,
            onValueChange = { it.also { password.value = it } },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(R.string.Password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.Password),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    ),
                )
            },
            visualTransformation =
            if (passwordVisibility.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    //TODO PERFORM ONCLICK VALIDATE
                }),
            trailingIcon = {
                val image =
                    if (passwordVisibility.value) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                IconButton(
                    onClick = { passwordVisibility.value = !passwordVisibility.value }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primary
            ),
            modifier = fieldModifier,
        )


        Spacer(modifier = Modifier.height(customSpacing.small))


    }
}

