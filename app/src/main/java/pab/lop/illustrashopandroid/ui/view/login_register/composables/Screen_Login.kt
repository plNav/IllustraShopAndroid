package pab.lop.illustrashopandroid.ui.view.login_register.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.*
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.utils.shoppingCartSelected
import pab.lop.illustrashopandroid.utils.userDefaultNoAuth
import pab.lop.illustrashopandroid.utils.userSelected
import pablo_lonav.android.utils.ScreenNav


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

    val keyboardController = LocalSoftwareKeyboardController.current

    //TODO DEV CREDENTIALS HARDCODED
    val email = remember { mutableStateOf("pab2@email.com") }
    val password = remember { mutableStateOf("1234") }

    var passwordVisibility by remember { mutableStateOf(false) }


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
            onClick = { validateLoginClick(
                context = context,
                loginRegisterViewModel = loginRegisterViewModel,
                navController = navController,
                email = email,
                password = password
            ) },
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
            onClick = { navController.navigate(ScreenNav.RegisterScreen.route) },
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


        Spacer(modifier = Modifier.height(customSpacing.mediumLarge))

        Text(
            text = stringResource(R.string.no_auth),
            color = MaterialTheme.colors.primary,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                userSelected = userDefaultNoAuth
                navController.navigate(ScreenNav.MainScreen.route)
            }
        )
    }
}

fun validateLoginClick(
    context: Context,
    loginRegisterViewModel: LoginRegisterViewModel,
    navController: NavController,
    email: MutableState<String>,
    password: MutableState<String>
) {
    if(email.value.isEmpty() || password.value.isEmpty())
        Toast.makeText(context, context.getString(R.string.login_incorrect), Toast.LENGTH_SHORT).show()
    else {
        loginRegisterViewModel.validateUser(email.value, password.value, onSuccessCallback = {
            userSelected = loginRegisterViewModel.currentUserResponse.value

            if(userSelected!!._id.isNotEmpty()){
                loginRegisterViewModel.getShoppingCartFromUser(userSelected!!._id){
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
