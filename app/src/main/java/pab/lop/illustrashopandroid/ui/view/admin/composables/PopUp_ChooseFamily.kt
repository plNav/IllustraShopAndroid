package pab.lop.illustrashopandroid.ui.view.admin.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.admin.AdminViewModel

@Composable
fun PopUp_Choose_Family(
    adminViewModel: AdminViewModel,
    applicationContext: Context,
    navController: NavController,
    scope: CoroutineScope,
    chooseFamiliesOpen: MutableState<Boolean>,
    customSpacing: Spacing,
    verticalGradient: Brush,
    verticalGradientDisabled: Brush,
    familyNames: MutableState<List<String>>,
    familiesToAssign: MutableState<List<String>>
) {

    Logger.wtf(familyNames.value.toString())

    Dialog(onDismissRequest = { chooseFamiliesOpen.value = false }) {
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
                        text = stringResource(R.string.choose_families).uppercase(),
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
                        onClick = { chooseFamiliesOpen.value = false },
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




                LazyColumn() {
                    items(familyNames.value) { family ->
                        LabelledCheckbox(family, familiesToAssign)
                    }
                }

                Column(Modifier.padding(customSpacing.mediumSmall)) {

                    /************ OK ************/
                    Text(
                        text = stringResource(R.string.Ok),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(color = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                brush =
                                if (!familiesToAssign.value.isNullOrEmpty()) verticalGradient
                                else verticalGradientDisabled
                            )
                            .padding(12.dp)
                            .clickable(onClick = {
                                if (!familiesToAssign.value.isNullOrEmpty()) {
                                    chooseFamiliesOpen.value = false
                                }
                            })
                    )
                    Text("")

                }
            }
        }
    }
}

@Composable
fun LabelledCheckbox(label: String, familiesToAssign: MutableState<List<String>>) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        val isChecked = remember { mutableStateOf(false) }
        Checkbox(
            checked =
            if (familiesToAssign.value.isNullOrEmpty()) false
            else familiesToAssign.value.contains(label),
            onCheckedChange = {
                isChecked.value = it
                if (isChecked.value) {
                    if (familiesToAssign.value.isNullOrEmpty()) familiesToAssign.value = listOf(label)
                    else {
                        if (!familiesToAssign.value.contains(label)) {
                            val change = mutableListOf(label)
                            change.addAll(familiesToAssign.value)
                            familiesToAssign.value = change
                        }
                    }
                } else {
                    if (familiesToAssign.value.isNullOrEmpty()) Logger.wtf("Families Null?")
                    else {
                        val change = mutableListOf<String>()
                        change.addAll(familiesToAssign.value)
                        change.remove(label)
                        familiesToAssign.value = change
                    }
                }
            },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Green)
        )
        Text(text = label)
    }
}
