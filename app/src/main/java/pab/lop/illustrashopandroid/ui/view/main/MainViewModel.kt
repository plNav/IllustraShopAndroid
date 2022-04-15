package pab.lop.illustrashopandroid.ui.view.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.data.api.ApiServices
import pab.lop.illustrashopandroid.data.model.UserModel

class MainViewModel : ViewModel() {

    var allUsersClientResponse: List<UserModel> by mutableStateOf(listOf())


    private var errorMessage: String by mutableStateOf("")


    fun getAllUsers(onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()

            try {
                allUsersClientResponse = apiServices.getAllUsers()
                Logger.i("get all users OK")
                onSuccessCallback()

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Logger.e("FAILURE getAllUsers \n $e")
            }
        }
    }
}