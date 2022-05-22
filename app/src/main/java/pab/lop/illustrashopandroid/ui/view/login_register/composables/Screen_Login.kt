package pab.lop.illustrashopandroid.ui.view.login_register.composables

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.shoppin.shopping_cart_request
import pab.lop.illustrashopandroid.data.model.user.user_google
import pab.lop.illustrashopandroid.data.model.user.user_request
import pab.lop.illustrashopandroid.ui.theme.*
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.utils.AuthResult
import pab.lop.illustrashopandroid.utils.admob.composables.InterstitialButton
import pab.lop.illustrashopandroid.utils.shoppingCartSelected
import pab.lop.illustrashopandroid.utils.userSelected
import pab.lop.illustrashopandroid.utils.ScreenNav


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Login(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel,
    context: Context,
    customSpacing: Spacing
) {

    val verticalGradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant),
        startY = 0f,
        endY = 100f
    )
    var isLoading = remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    //TODO DEV CREDENTIALS HARDCODED
    val email = remember { mutableStateOf("pab@email.com") }
    val password = remember { mutableStateOf("1234") }

    var passwordVisibility by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val text = remember { mutableStateOf<String?>(null) }
    val user = remember(loginRegisterViewModel) { loginRegisterViewModel.user }.collectAsState()
    val signInRequestCode = 1

    val authResultLauncher = rememberLauncherForActivityResult(contract = AuthResult()) { task ->
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account == null) {
                text.value = "Google Sign In Failed"

            } else {
                scope.launch {
                    loginRegisterViewModel.setSignInValue(
                        email = account.email!!,
                        displayName = account.displayName!!
                    )
                }
            }

        } catch (e: ApiException) {
            text.value = e.localizedMessage
        }
    }
    user.value?.let {
        Logger.wtf("user with google ok ${user.value!!.email}")
        //GoogleSignInScreen(user.value!!)
        loginRegisterViewModel.getAllEmails {
            if (loginRegisterViewModel.emailListResponse.contains(user.value!!.email)) {
                loginRegisterViewModel.getUserByEmail(user.value!!.email) {
                    userSelected = loginRegisterViewModel.currentUserResponse.value
                    loginRegisterViewModel.getShoppingCartFromUser(userSelected!!._id) {
                        shoppingCartSelected = loginRegisterViewModel.currentShoppingCartResponse.value
                        navController.navigate(ScreenNav.MainScreen.route)
                    }
                }
            } else {
                val userGoogle = user_request(
                    username = user.value!!.displayName,
                    email = user.value!!.email,
                    password = " ",
                    google = true,
                    address = "",
                    country = "",
                    postal_code = "",
                    pay_method = "",
                    phone = "",
                    pay_number = "",
                    name = "",
                    last_name = "",
                    rol = "STANDARD"
                )
                loginRegisterViewModel.createUser(userGoogle) {
                    userSelected = loginRegisterViewModel.currentUserResponse.value
                    loginRegisterViewModel.createShoppingCart(shopping_cart_request(userSelected!!._id)) {
                        navController.navigate(ScreenNav.MainScreen.route)
                    }

                }
            }
        }
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = customSpacing.large)

    ) {

        Spacer(modifier = Modifier.height(customSpacing.small))

        Text(
            text = stringResource(R.string.login),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(customSpacing.mediumLarge))

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
            modifier = Modifier
                .fillMaxWidth()
                .height(customSpacing.extraLarge + customSpacing.large)
                .padding((customSpacing.small + customSpacing.extraSmall)),
        )

        Spacer(modifier = Modifier.height(customSpacing.mediumMedium))

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
            if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            trailingIcon = {
                val image =
                    if (passwordVisibility) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                IconButton(
                    onClick = { passwordVisibility = !passwordVisibility }
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
            modifier = Modifier
                .fillMaxWidth()
                .height(customSpacing.extraLarge + customSpacing.large)
                .padding((customSpacing.small + customSpacing.extraSmall)),
        )

        Spacer(modifier = Modifier.height(customSpacing.large))

        /************ VALIDATE ************/
        Button(
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = {
                validateLoginClick(
                    context = context,
                    loginRegisterViewModel = loginRegisterViewModel,
                    navController = navController,
                    email = email,
                    password = password
                )
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color(0xFFFFF5EE),
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .padding(customSpacing.default)

        ) {
            Text(
                text = stringResource(R.string.validate),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(verticalGradient)
                    .fillMaxWidth()
                    .padding((customSpacing.small + customSpacing.extraSmall))
            )
        }


        Spacer(modifier = Modifier.height(customSpacing.mediumLarge))

        /************ REGISTER ************/
        Button(
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = { navController.navigate(ScreenNav.RegisterScreen.withArgs(false)) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color(0xFFFFF5EE),
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .padding(customSpacing.default)

        ) {
            Text(
                text = stringResource(R.string.register),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(verticalGradient)
                    .fillMaxWidth()
                    .padding((customSpacing.small + customSpacing.extraSmall))
            )
        }

        GoogleSignInButton(text = "Sign In with Google",
            icon = painterResource(R.drawable.google),
            loadingText = "Signing In...",
            isLoading = isLoading.value,
            onClick = {
                isLoading.value = true
                text.value = null
                authResultLauncher.launch(signInRequestCode)
            }
        )

        text.value?.let {
            isLoading.value = false

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = it)
        }

        Spacer(modifier = Modifier.height(customSpacing.mediumLarge))

        /************NO REGISTER ************/
        InterstitialButton(navController = navController)
    }
}

fun validateLoginClick(
    context: Context,
    loginRegisterViewModel: LoginRegisterViewModel,
    navController: NavController,
    email: MutableState<String>,
    password: MutableState<String>
) {
    if (email.value.isEmpty() || password.value.isEmpty())
        Toast.makeText(context, context.getString(R.string.login_incorrect), Toast.LENGTH_SHORT).show()
    else {
        loginRegisterViewModel.validateUser(email.value, password.value, onSuccessCallback = {
            userSelected = loginRegisterViewModel.currentUserResponse.value

            if (userSelected!!._id.isNotEmpty()) {
                loginRegisterViewModel.getShoppingCartFromUser(userSelected!!._id) {
                    shoppingCartSelected = loginRegisterViewModel.currentShoppingCartResponse.value
                    Toast.makeText(
                        context, context.getString(R.string.login_correct) + "\n" + userSelected!!.username, Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(ScreenNav.MainScreen.route)
                }
            }
        }, onFailureCallback = {
            Toast.makeText(context, context.getString(R.string.login_incorrect), Toast.LENGTH_SHORT).show()
        })
    }
}


//NEWWWWWWWWWWWWWWWW
fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, signInOption)
}

@Composable
fun SignInScreen(signInViewModel: LoginRegisterViewModel) {
    val scope = rememberCoroutineScope()
    val text = remember { mutableStateOf<String?>(null) }
    val user = remember(signInViewModel) { signInViewModel.user }.collectAsState()
    val signInRequestCode = 1

    val authResultLaucher = rememberLauncherForActivityResult(contract = AuthResult()) { task ->
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account == null) {
                text.value = "Google Sign In Failed"

            } else {
                scope.launch {
                    signInViewModel.setSignInValue(
                        email = account.email!!,
                        displayName = account.displayName!!
                    )
                }
            }

        } catch (e: ApiException) {
            text.value = e.localizedMessage
        }
    }

    AuthView(errorText = text.value, onClick = {
        text.value = null
        authResultLaucher.launch(signInRequestCode)
    }
    )
    user.value?.let {
        GoogleSignInScreen(user.value!!)
    }

}

@Composable
fun AuthView(
    errorText: String?,
    onClick: () -> Unit
) {
    var isLoading = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GoogleSignInButton(text = "Sign In with Google",
            icon = painterResource(R.drawable.google),
            loadingText = "Signing In...",
            isLoading = isLoading.value,
            onClick = {
                isLoading.value = true
                onClick()
            }
        )

        errorText?.let {
            isLoading.value = false

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = it)
        }
    }
}

@Composable
fun GoogleSignInScreen(user: user_google) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign In Success")
        Text(user.email)
        Text(user.displayName)
    }
}


