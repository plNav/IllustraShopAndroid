package pab.lop.illustrashopandroid.ui.view.login_register.composables

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.utils.regexSpecialChars

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicInfo(
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
    context: Context,
    isEditionMode: Boolean,
    passwordUpdate: MutableState<Boolean>
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
                        Patterns.EMAIL_ADDRESS.matcher(email.value).matches() -> emailChecked.value = true
                        else -> emailChecked.value = false
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            label = {
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
                            Patterns.EMAIL_ADDRESS.matcher(email.value).matches() -> Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
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
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            label = {
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

        if(isEditionMode){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .clickable(
                        indication = rememberRipple(color = MaterialTheme.colors.primary),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            passwordUpdate.value = !passwordUpdate.value
                            if(!passwordUpdate.value) passwordChecked.value = true
                            else {
                                password1.value = ""
                                password2.value = ""
                                passwordChecked.value = false
                            }
                        }
                    )
                    .requiredHeight(ButtonDefaults.MinHeight)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = passwordUpdate.value,
                    onCheckedChange = null
                )

                Spacer(Modifier.size(6.dp))

                Text(
                    text = stringResource(R.string.update_password)
                )
            }
        }

        /************ PASSWORD ************/
        OutlinedTextField(
            enabled = if(!isEditionMode) true else passwordUpdate.value,
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
            enabled = if(!isEditionMode) true else passwordUpdate.value,
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
            visualTransformation =
            if (passwordVisibility.value) VisualTransformation.None
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
