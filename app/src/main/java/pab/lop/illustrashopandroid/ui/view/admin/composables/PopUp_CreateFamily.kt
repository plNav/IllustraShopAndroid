package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.family.family_request
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel
import pab.lop.illustrashopandroid.utils.familyNameList
import pab.lop.illustrashopandroid.utils.regexSpecialChars

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopUp_Create_Family(
    applicationContext: Context,
    navController: NavController,
    scope: CoroutineScope,
    adminViewModel: AdminViewModel,
    createFamilyOpen: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradient: Brush,
    verticalGradiendDisabled: Brush,
) {
    val customName = remember { mutableStateOf("") }
    val nameVerified = remember { mutableStateOf(false) }

    Logger.wtf("in popup ===>> $familyNameList")





    Dialog(onDismissRequest = { createFamilyOpen.value = false }) {
        Surface(
            modifier = Modifier
                .padding(customSpacing.small),
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
                        text = stringResource(R.string.new_family).uppercase(),
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
                        onClick = { createFamilyOpen.value = false },
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
                        //.fillMaxSize()
                        .padding(horizontal = customSpacing.mediumSmall)

                ) {


                    /************ NAME ************/
                    OutlinedTextField(
                        value = customName.value,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        onValueChange = {
                            it.also {
                                customName.value = it
                                when {
                                    it.contains(regexSpecialChars) -> {
                                        nameVerified.value = false
                                        Toast.makeText(applicationContext, "Special characters not allowed", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    familyNameList.contains(it.trim()) -> {
                                        nameVerified.value = false
                                        Toast.makeText(applicationContext, "Already in use", Toast.LENGTH_SHORT).show()
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
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier.height(
                            customSpacing.mediumMedium
                        )
                    )

                    /************ OK ************/
                    Text(
                        text = stringResource(R.string.Ok),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = if (nameVerified.value) verticalGradient else verticalGradiendDisabled)
                            .padding(12.dp)
                            .clickable(onClick = {
                                if (nameVerified.value) {
                                    adminViewModel.createFamily(family_request(name = customName.value)) {
                                        createFamilyOpen.value = false
                                    }
                                }
                            })
                    )
                    Text("") // ÑAPAS
                }
            }
        }
    }
}
