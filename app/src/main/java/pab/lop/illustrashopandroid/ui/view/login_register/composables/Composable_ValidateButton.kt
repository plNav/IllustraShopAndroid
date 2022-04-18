package pab.lop.illustrashopandroid.ui.view.login_register.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pab.lop.illustrashopandroid.R
import pab.lop.illustrashopandroid.data.model.user.user_request
import pab.lop.illustrashopandroid.ui.theme.Spacing
import pab.lop.illustrashopandroid.ui.view.login_register.LoginRegisterViewModel
import pab.lop.illustrashopandroid.utils.getSHA256
import pab.lop.illustrashopandroid.utils.userSelected
import pablo_lonav.android.utils.ScreenNav

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