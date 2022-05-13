package pab.lop.illustrashopandroid.ui.view.login_register.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.utils.ScreenNav
import pab.lop.illustrashopandroid.utils.getSHA256
import pab.lop.illustrashopandroid.utils.userSelected

@Composable
fun PopUpPassword(
    popUpPasswordOpen: MutableState<Boolean>,
    passwordValidated: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradient: Brush,
    context: Context,
    allFields: Boolean,
    name: MutableState<String>,
    lastName: MutableState<String>,
    username: MutableState<String>,
    email: MutableState<String>,
    phone: MutableState<String>,
    postalCode: MutableState<String>,
    address: MutableState<String>,
    country: MutableState<String>,
    navController: NavController,
    loginRegisterViewModel: LoginRegisterViewModel,
) {
    val password = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    Dialog(
        onDismissRequest = { popUpPasswordOpen.value = false }
    ) {
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
                        text = stringResource(R.string.old_password),
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
                        onClick = { popUpPasswordOpen.value = false },
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
                        visualTransformation =
                        if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                        ),
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
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.mediumSmall
                        )
                    )


                    /************ OK ************/
                    Text(
                        text = stringResource(R.string.validate),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = verticalGradient)
                            .padding(12.dp)
                            .clickable(onClick = {
                                val passwordCyphered = getSHA256(password.value)
                                if (passwordCyphered == userSelected!!.password) {
                                    userSelected!!.password = passwordCyphered
                                    // passwordValidated.value = true
                                    userSelected!!.name = if (!allFields) "" else name.value
                                    userSelected!!.last_name = if (!allFields) "" else lastName.value
                                    userSelected!!.username = username.value
                                    userSelected!!.email = email.value
                                    userSelected!!.phone = if (!allFields) userSelected!!.phone else phone.value
                                    userSelected!!.postal_code = if (!allFields) userSelected!!.postal_code else postalCode.value
                                    userSelected!!.address = if (!allFields) userSelected!!.address else address.value
                                    userSelected!!.country = if (!allFields) userSelected!!.country else country.value

                                    if (allFields)
                                        loginRegisterViewModel.updateUserComplete(
                                            id = userSelected!!._id,
                                            user = userSelected!!
                                        ) {
                                            navController.navigate(ScreenNav.MainScreen.route)
                                            Toast
                                                .makeText(
                                                    context,
                                                    context.getString(R.string.update_complete),
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                            popUpPasswordOpen.value = false

                                        }
                                    else
                                        loginRegisterViewModel.updateUserPartial(id = userSelected!!._id, user = userSelected!!) {
                                            navController.navigate(ScreenNav.MainScreen.route)
                                            Toast
                                                .makeText(
                                                    context,
                                                    context.getString(R.string.update_complete),
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                            popUpPasswordOpen.value = false

                                        }

                                } else {
                                    popUpPasswordOpen.value = false
                                    Toast
                                        .makeText(context, context.getString(R.string.password_incorrect), Toast.LENGTH_SHORT)
                                        .show()
                                }
                            })
                    )
                    Text("")
                }
            }
        }
    }
}