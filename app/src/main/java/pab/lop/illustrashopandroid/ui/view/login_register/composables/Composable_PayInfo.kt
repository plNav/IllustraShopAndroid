package pab.lop.illustrashopandroid.ui.view.login_register.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PayInfo(
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

