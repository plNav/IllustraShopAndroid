package pab.lop.illustrashopandroid.ui.view.login_register.composables

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import pab.lop.illustrashopandroid.utils.regexSpecialChars

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PayInfo(
    customSpacing: Spacing,
    isEditionMode: Boolean,
    name: MutableState<String>,
    nameChecked: MutableState<Boolean>,
    lastName: MutableState<String>,
    lastNameChecked: MutableState<Boolean>,
    country: MutableState<String>,
    countryChecked: MutableState<Boolean>,
    address: MutableState<String>,
    addressChecked: MutableState<Boolean>,
    postalCode: MutableState<String>,
    postalCodeChecked: MutableState<Boolean>,
    phone: MutableState<String>,
    phoneChecked: MutableState<Boolean>,
    context: Context
) {

    val spaceBetweenFields = customSpacing.small
    val emptyFieldError = context.getString(R.string.emptyFieldError)
    val fieldOK = context.getString(R.string.fieldOK)
    val specialCharError = context.getString(R.string.specialCharError)


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

        /************ NAME ************/
        OutlinedTextField(
            value = name.value,
            onValueChange = {
                it.also {
                    name.value = it
                    nameChecked.value = name.value.isNotEmpty()
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
                    text = stringResource(R.string.name),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Person
                IconButton(
                    onClick = {
                        if (nameChecked.value) Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
                        else Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (nameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (nameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (nameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (nameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (nameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ LAST NAME ************/
        OutlinedTextField(
            value = lastName.value,
            onValueChange = {
                it.also {
                    lastName.value = it
                    lastNameChecked.value = lastName.value.isNotEmpty()
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
                    text = stringResource(R.string.lastName),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Person
                IconButton(
                    onClick = {
                        if (lastNameChecked.value) Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
                        else Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (lastNameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (lastNameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (lastNameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (lastNameChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (lastNameChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ COUNTRY ************/
        OutlinedTextField(
            value = country.value,
            onValueChange = {
                it.also {
                    country.value = it
                    countryChecked.value = country.value.isNotEmpty()
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(
                    text = stringResource(R.string.country),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Apartment
                IconButton(
                    onClick = {
                        if (countryChecked.value) Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
                        else Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (countryChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (countryChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (countryChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (countryChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (countryChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ ADDRESS ************/
        OutlinedTextField(
            value = address.value,
            onValueChange = {
                it.also {
                    address.value = it
                    addressChecked.value = address.value.isNotEmpty()
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
                    text = stringResource(R.string.address),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Apartment
                IconButton(
                    onClick = {
                        if (addressChecked.value) Toast.makeText(context, fieldOK, Toast.LENGTH_SHORT).show()
                        else Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Icon(
                        imageVector = image,
                        stringResource(R.string.Password)
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (addressChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (addressChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (addressChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (addressChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (addressChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

        /************ POSTAL CODE ************/
        OutlinedTextField(
            value = postalCode.value,
            onValueChange = {
                it.also {
                    postalCode.value = it
                    postalCodeChecked.value = !(it.contains(regexSpecialChars) || it.isEmpty())
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(
                    text = stringResource(R.string.postalCode),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Apartment
                IconButton(
                    onClick = {
                        when {
                            postalCode.value.isEmpty() -> Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                            postalCode.value.contains(regexSpecialChars) -> Toast.makeText(context, specialCharError, Toast.LENGTH_SHORT).show()
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
                focusedBorderColor = if (postalCodeChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (postalCodeChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (postalCodeChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (postalCodeChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (postalCodeChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))


        /************ PHONE ************/
        OutlinedTextField(
            value = phone.value,
            onValueChange = {
                it.also {
                    phone.value = it
                    phoneChecked.value = !( it.contains(regexSpecialChars) || it.isEmpty())
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            label = {
                Text(
                    text = stringResource(R.string.phone),
                    style = TextStyle(
                        color = MaterialTheme.colors.secondary
                    )
                )
            },
            trailingIcon = {
                val image = Icons.Filled.Phone
                IconButton(
                    onClick = {
                        when {
                            phone.value.isEmpty() -> Toast.makeText(context, emptyFieldError, Toast.LENGTH_SHORT).show()
                            phone.value.contains(regexSpecialChars) -> Toast.makeText(context, specialCharError, Toast.LENGTH_SHORT).show()
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
                focusedBorderColor = if (phoneChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedBorderColor = if (phoneChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                focusedLabelColor = if (phoneChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary,
                unfocusedLabelColor = if (phoneChecked.value) MaterialTheme.colors.secondary else MaterialTheme.colors.onSecondary,
                cursorColor = if (phoneChecked.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
            ),
            modifier = fieldModifier,
        )

        Spacer(modifier = Modifier.height(spaceBetweenFields))

    }

}


