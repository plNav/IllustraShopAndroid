package pab.lop.illustrashopandroid.ui.view.login_register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.*
import pablo_lonav.android.utils.ScreenNav


@Composable
fun Login(
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel,
    context: Context,
    customSpacing: Spacing
) {

    IllustraShopAndroidTheme {

        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val emptyError = stringResource(R.string.emptyError)

        var passwordVisibility by remember { mutableStateOf(false) }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = customSpacing.extraLarge)

        ) {

            Spacer(modifier = Modifier.height(customSpacing.small))

            Text(
                text = stringResource(R.string.login),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(customSpacing.mediumLarge))

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
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(customSpacing.mediumMedium))

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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(customSpacing.large))

            Button(
                onClick = {
                    if (false/*email.value.isEmpty() || password.value.isEmpty()*/)
                        Toast.makeText(context, emptyError, Toast.LENGTH_SHORT).show()
                    else {
                        navController.navigate(ScreenNav.ValidateScreen.route)
                        //backLogin = true
                        //loginRegisterViewModel.validateClient(email = email.value, passw = password.value)
                        //navController.navigate(ScreenNav.ValidateLoginScreen.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(customSpacing.superLarge),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            ) {
                Text(
                    text = stringResource(R.string.validate),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(customSpacing.mediumLarge))

            Button(
                onClick = {
                    // navController.navigate(ScreenNav.RegisterScreen.route)
                          navController.navigate(ScreenNav.MainScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(customSpacing.superLarge),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}