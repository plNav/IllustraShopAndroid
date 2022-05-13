package pab.lop.illustrashopandroid.ui.view.login_register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.data.api.ApiServices
import kotlinx.coroutines.*
import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.shoppin.shopping_cart_request
import pab.lop.illustrashopandroid.data.model.shopping_cart.shopping_cart_response
import pab.lop.illustrashopandroid.data.model.user.user_request
import pab.lop.illustrashopandroid.data.model.user.user_response
import pab.lop.illustrashopandroid.utils.getSHA256
import retrofit2.Response

class LoginRegisterViewModel : ViewModel() {

    var currentUserResponse: MutableState<user_response?> = mutableStateOf(null)
    var currentShoppingCartResponse : MutableState<shopping_cart_response?> = mutableStateOf(null)


    var usernameListResponse : List<String> by mutableStateOf(listOf())
    var emailListResponse : List<String> by mutableStateOf(listOf())

    var updateOkResponse : Boolean by mutableStateOf(false)


    private var errorMessage: String by mutableStateOf("")


    fun validateUser(email: String, passw: String, onSuccessCallback: () -> Unit, onFailureCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try {
                val passwEncrypt = getSHA256(passw)
                val list: Response<List<user_response>> = apiServices.validateClient(email, passwEncrypt)

                if (list.isSuccessful && list.body()!!.isNotEmpty()) {
                    if (list.body()!!.size == 1) {
                        currentUserResponse.value = list.body()!![0]
                        Logger.i("Validation correct ${list.body()!![0]}")
                        onSuccessCallback()
                    } else{
                        Logger.wtf("ValidateClient brings more than one for $email - $passwEncrypt")
                        onFailureCallback()
                    }
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                onFailureCallback()
                Logger.e("FAILURE validate client")
            }
        }
    }


    fun getAllEmails(onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        try{
            viewModelScope.launch {
                val response : Response<List<String>> = apiServices.getAllEmail()
                if(response.isSuccessful){
                    Logger.i("Get emails OK \n${response.body().toString()}")
                    emailListResponse = response.body() as List<String>

                    Logger.wtf("${emailListResponse}")
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
            Logger.e("FAILURE getting all family names")
        }
    }

    fun getAllUsernames(onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        try{
            viewModelScope.launch {
                val response : Response<List<String>> = apiServices.getAllUsernames()
                if(response.isSuccessful){
                    Logger.i("Get Family Names OK \n${response.body().toString()}")
                    usernameListResponse = response.body() as List<String>

                    Logger.wtf("${usernameListResponse as List<String>}")
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
            Logger.e("FAILURE getting all family names")
        }
    }

    fun createUser(newUser: user_request, onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try{
                val response : Response<user_response> = apiServices.createUser(newUser)
                if(response.isSuccessful){
                    currentUserResponse.value = response.body()
                    Logger.i("Create User SUCCESSFUL \n $response \n ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("Error Response Create User $response")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create User")
            }
        }
    }

    fun createShoppingCart(newShoppingCart : shopping_cart_request, onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try{
                val response : Response<shopping_cart_response> = apiServices.createShoppingCart(newShoppingCart)
                if(response.isSuccessful){
                    currentShoppingCartResponse.value = response.body()
                    Logger.i("Create Shopping Cart OK \n $response \n ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("Error Response  Shopping Cart $response")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create User")
            }
        }
    }

    fun getShoppingCartFromUser(id_user: String, onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        try{
            viewModelScope.launch {
                val response : Response<shopping_cart_response> = apiServices.getShoppingCart(id_user)
                if(response.isSuccessful){
                    Logger.i("Get ShoppingCart \n${response.body().toString()}")
                    currentShoppingCartResponse.value = response.body()
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
            Logger.e("FAILURE getting shoppingCarts")
        }
    }

    fun updateUserPartial(id: String, user: user_response, onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                val response = apiServices.updateUserPartial(id = id, user = user)
                if (response.isSuccessful){
                    updateOkResponse = true
                    Logger.i("SUCCESS updateOrder $response ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("FAILURE response updateUser $user ")

            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE update user\n${e.message.toString()}")
            }
        }
    }
    fun updateUserComplete(id: String, user: user_response, onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                val response = apiServices.updateUserComplete(id = id, user = user)
                if (response.isSuccessful){
                    updateOkResponse = true
                    Logger.i("SUCCESS updateOrder $response ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("FAILURE response updateUser $user ")

            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE update user\n${e.message.toString()}")
            }
        }
    }
}