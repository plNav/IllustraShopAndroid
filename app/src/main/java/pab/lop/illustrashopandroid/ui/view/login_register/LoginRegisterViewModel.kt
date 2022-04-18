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
import pab.lop.illustrashopandroid.data.model.user.user_request
import pab.lop.illustrashopandroid.data.model.user.user_response
import pab.lop.illustrashopandroid.utils.getSHA256
import retrofit2.Response

/**
 * Logica asincrona de llamadas a la Api,
 * vinculado a las vistas del paquete login_register
 * Las funciones se ejecutan en el ViewModelScope y
 * llaman a la instancia de Retrofit con sus rutas.
 */
class LoginRegisterViewModel : ViewModel() {

    var allUsersClientResponse: List<user_response> by mutableStateOf(listOf())

    var currentUserResponse: MutableState<user_response?> = mutableStateOf(null)

    var usernameListResponse : List<String> by mutableStateOf(listOf())
    var emailListResponse : List<String> by mutableStateOf(listOf())




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


    fun validateUser(email: String, passw: String, onSuccessCallback: () -> Unit) {
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
                    } else Logger.wtf("ValidateClient brings more than one for $email - $passwEncrypt")
                }
            } catch (e: Exception) {
                errorMessage = e.message.toString()
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

    //var adminsClientResponse : List<UserModel> by mutableStateOf(listOf())


/*


/*    fun checkEmail(email: String, onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try{

                val res = apiServices.checkEmail(email)
                if(res.isSuccessful){
                    emailExistsResponse = res.body()!!
                    Logger.i("CORRECT checkEmail $email, $emailExistsResponse")
                    onSuccessCallback()
                }else Logger.e("FAILURE checkEmail $email")


            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE checkEmail $email, $emailExistsResponse")
            }
        }
    }

    fun checkUsername(username: String, onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try{
                val res = apiServices.checkUserName(username)
                if(res.isSuccessful){
                    usernameExistsResponse = res.body()!!
                    Logger.i("CORRECT checkEmail $username, $usernameExistsResponse")
                    onSuccessCallback()
                }else Logger.e("FAILURE checkEmail $username")


            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE checkusername $username, $emailExistsResponse")
            }
        }
    }*/

    var emailExistsResponse : Boolean by mutableStateOf(true)
    var currentClientResponse : ClientModel by mutableStateOf(
        ClientModel("Error", "Error", "Error", "Error"))

    var currentZoneResponse : ZoneModel by mutableStateOf(
        ZoneModel("Error", "Error", "Error"))

    var currentUserResponse : UserModel by mutableStateOf(
        UserModel("Error", "Error", "Error", "Error", "Error", "Error", false))

    var currentTableResponse : TableModel by mutableStateOf(
        TableModel("Error", "Error", true, false, "Error", 6, 1,1,10,"Error", "Error"))

    var currentFamilyResponse : FamilyModel by mutableStateOf(
        FamilyModel("Error", "Error", "Error")
    )

    private var errorMessage : String by mutableStateOf("")

    fun checkEmail(email : String){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                emailExistsResponse = apiServices.checkEmail(email)
                Logger.i("CORRECT checkEmail $email, $emailExistsResponse")

                async{

                }

            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE checkEmail $email, $emailExistsResponse")
            }
        }
    }

    fun validateClient(email: String, passw : String) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                val passwEncrypt = getSHA256(passw)
                val list : List<ClientModel> =  apiServices.validateClient(email, passwEncrypt)

                if(list.isNotEmpty()){
                    currentClientResponse = list[0]
                    if(currentClientResponse._id != "Error") currentClient = currentClientResponse
                }
                Logger.i("Result:\n$currentClientResponse \n$currentClient")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE validate client")
            }
        }
    }

    fun createClient(client: ClientPost){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                val response : Response<ClientModel> = apiServices.createClient(client)
                if(response.isSuccessful){
                    currentClientResponse = response.body()!!
                    currentClient = currentClientResponse
                    Logger.i("Create client SUCCESSFUL \n $response \n ${response.body()}")
                }else Logger.e("Error Response Create Client $response")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create client")
            }
        }
    }

    fun getClientUsersList(){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                allUsersClientResponse = apiServices.getClientUsers(currentClient._id)
                Logger.i("CORRECT getClientUsersList ")
            }catch (e : java.lang.Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE getClientUsersList ")
            }
        }
    }

    private fun createUser(user : UserPost){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                val response : Response<UserModel> = apiServices.createUser(user)
                if(response.isSuccessful){
                    currentUserResponse = response.body()!!
                    currentUser = currentUserResponse
                    Logger.i("Create User SUCCESSFUL \n $response \n ${response.body()}")
                }else Logger.e("Error Response Create User")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create User")
            }
        }
    }

    private fun createZone(zone : ZonePost){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                val response : Response<ZoneModel> = apiServices.createZone(zone)
                if(response.isSuccessful){
                    currentZoneResponse = response.body()!!
                    currentZone = currentZoneResponse
                    Logger.i("Create Zone SUCCESSFUL \n $response \n ${response.body()}")
                }else Logger.e("Error Response Create Zone")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create Zone")
            }
        }
    }

    private fun createFamily(family : FamilyPost){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                val response : Response<FamilyModel> = apiServices.createFamily(family)
                if(response.isSuccessful){
                    currentFamilyResponse = response.body()!!
                    currentFamily = currentFamilyResponse
                    Logger.i("Create Family SUCCESSFUL \n $response \n ${response.body()}")
                }else Logger.e("Error Response Create Family")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create Family")
            }
        }
    }

    private fun createProduct(product : ProductPost){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                val response : Response<ProductModel> = apiServices.createProduct(product)
                if(response.isSuccessful){
                    //  currentZoneResponse = response.body()!!
                    //  currentZone = currentZoneResponse
                    Logger.i("Create Product SUCCESSFUL \n $response \n ${response.body()}")
                }else Logger.e("Error Response Create Product")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create Product")
            }
        }
    }

    private fun createTable(table : TablePost){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                val response : Response<TableModel> = apiServices.createTable(table)
                if(response.isSuccessful){
                    currentTableResponse = response.body()!!
                    currentTable = currentTableResponse
                    Logger.i("Create Table SUCCESSFUL \n $response \n ${response.body()}")
                }else Logger.e("Error Response Create Table")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create Table")
            }
        }
    }

    fun createDefaults(){
        viewModelScope.launch {
            defaultAdmin.id_client = currentClient._id
            createUser(defaultAdmin)
            createFamily(defaultFamily)
            defaultZone.id_client = currentClient._id
            createZone(defaultZone)
            defaultFamily.id_client = currentClient._id
            delay(500)
            currentZone = currentZoneResponse
            defaultTable.id_zone = currentZoneResponse._id
            defaultTable.num_row = 0
            for ( i in 1 until 3){
                defaultTable.num_column = 0
                for(j in 1 until 7){
                    defaultTable.name = "${defaultTable.num_row}${defaultTable.num_column}"
                    createTable(defaultTable)
                    defaultTable.num_column++
                    if(defaultTable.num_column == 6) defaultTable.num_row++
                }
            }
            defaultProduct1.id_client = currentClient._id
            defaultProduct1.id_familia = currentFamilyResponse._id
            createProduct(defaultProduct1)
            defaultProduct2.id_client = currentClient._id
            defaultProduct2.id_familia = currentFamilyResponse._id
            createProduct(defaultProduct2)

     */
/*       delay(300)
            defaultZone2.id_client = currentClient._id
            createZone(defaultZone2)
            defaultFamily.id_client = currentClient._id
            delay(500)
            currentZone = currentZoneResponse
            defaultTable2.id_zone = currentZoneResponse._id
            defaultTable2.num_row = 0
            defaultTable2.num_column = 0
            for ( i in 1 until 3){
                defaultTable2.num_column = 0
                for(j in 1 until 7){
                    defaultTable2.name = "${defaultTable.num_row}${defaultTable2.num_column}"
                    createTable(defaultTable2)
                    defaultTable2.num_column++
                    if(defaultTable2.num_column == 5) defaultTable2.num_row++
                }
            }*//*


        }
    }


*/
/*    fun getClientAdmins(){
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try{
                adminsClientResponse = apiServices.getClientAdmin(currentClient._id)
                Logger.i("CORRECT getClientAdmins ")
            }catch (e: java.lang.Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE getClientAdmins ")
            }
        }
    }*/


}