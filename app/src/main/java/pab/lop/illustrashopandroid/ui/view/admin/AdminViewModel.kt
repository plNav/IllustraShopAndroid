package pab.lop.illustrashopandroid.ui.view.admin

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper.getMainLooper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import pab.lop.illustrashopandroid.data.api.ApiServices
import pab.lop.illustrashopandroid.data.model.family.family_request
import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_request
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.data.model.user.user_response
import pab.lop.illustrashopandroid.utils.ProgressRequestBody
import retrofit2.Response
import retrofit2.await
import java.io.*


class AdminViewModel : ViewModel() {

    private var errorMessage: String by mutableStateOf("")
    var familyProductsResponse: HashMap<String, List<product_stock_response>> by mutableStateOf(hashMapOf())
    private var byteArray: ByteArray? by mutableStateOf(null)
    private var currentFamilyResponse : family_response by mutableStateOf(family_response("",""))
    var familyNameListResponse : List<String> by mutableStateOf(listOf())
    var productListResponse : List<product_stock_response> by mutableStateOf(listOf())
    var familyListResponse : List<family_response> by mutableStateOf(listOf())
    var updateOkResponse : Boolean by mutableStateOf(false)
    var allOrdersResponse : List<order_response> by mutableStateOf(listOf())

    fun createFamily(family: family_request, onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try{
                val response : Response<family_response> = apiServices.createFamily(family)
                if(response.isSuccessful){
                    currentFamilyResponse = response.body()!!
                    Logger.i("Create Family SUCCESSFUL \n $response \n ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("Error Response Create Family $response")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create Family")
            }
        }
    }

    fun getFamilyNames(onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        try{
            viewModelScope.launch {
                val response : Response<List<String>> = apiServices.getFamilyNames()
                if(response.isSuccessful){
                    Logger.i("Get Family Names OK \n${response.body().toString()}")
                    familyNameListResponse = response.body() as List<String>

                    Logger.wtf("${familyNameListResponse as List<String>}")
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
            Logger.e("FAILURE getting all family names")
        }
    }

    fun getFamilies(onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()
        try{
            viewModelScope.launch {
                val response : Response<List<family_response>> = apiServices.getFamilies()
                if(response.isSuccessful){
                    Logger.i("Get All Products OK \n${response.body().toString()}")
                    familyListResponse = response.body() as List<family_response>
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
            Logger.e("FAILURE getting all family names")
        }
    }

    fun getProducts(onSuccessCallback: () -> Unit){
        val apiServices = ApiServices.getInstance()
        try{
            viewModelScope.launch {
                val response : Response<List<product_stock_response>> = apiServices.getProducts()
                if(response.isSuccessful){
                    Logger.i("Get All Products OK \n${response.body().toString()}")
                    productListResponse = response.body() as List<product_stock_response>
                    onSuccessCallback()
                }
            }
        }catch (e: Exception){
            errorMessage = e.message.toString()
            Logger.e("FAILURE getting all family names")
        }
    }



    fun createProductStock(newProduct: product_stock_request, onSuccessCallback: () -> Unit) {
        val apiServices = ApiServices.getInstance()

        viewModelScope.launch {
            try{
                val response : Response<product_stock_response> = apiServices.createProductStock(newProduct)
                if(response.isSuccessful){
                   // currentFamilyResponse = response.body()!!
                    Logger.i("Create Product Stock SUCCESSFUL \n $response \n ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("Error Response Create Product Stock $response")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE create Family")
            }
        }
    }

    fun multipartImageUpload(
        byteArray: MutableState<ByteArray?>,
        context: Context,
        bitmap: MutableState<Bitmap?>,
        customName: MutableState<String>,
        onSuccess: () -> Unit
    ) {
        val apiService = ApiServices.getInstance()
        try {
            viewModelScope.launch {
                if (bitmap.value != null) {

                    val bos = ByteArrayOutputStream()
                    bitmap.value?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                    byteArray.value = bos.toByteArray()

                    val filesDir: File = context.filesDir
                    val file = File(filesDir, customName.value + ".jpg")
                    val fos = FileOutputStream(file)

                    fos.write(byteArray.value)
                    fos.flush()
                    fos.close()

                    val fileBody = ProgressRequestBody(file)
                    val body: MultipartBody.Part = createFormData("upload", file.name, fileBody)
                    val name = RequestBody.create("text/plain".toMediaTypeOrNull(), "upload")

                    val mainHandler = Handler(getMainLooper())

                    val runnable = Runnable {
                        viewModelScope.async {
                            apiService.postImage(image = body, name = name).await()
                        }
                    }
                    mainHandler.postDelayed(runnable, 200)  //delay)
                    onSuccess()
                }
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Logger.wtf("ERROR UPLOAD FILE NOT FOUND EXCEPTION \n ${e.message}")

        } catch (e: IOException) {
            e.printStackTrace()
            Logger.wtf("ERROR UPLOAD IO EXCEPTION \n ${e.message}")

        } catch (e: Exception) {
            e.printStackTrace()
            Logger.wtf("ERROR UPLOAD UNKNOWN EXCEPTION \n ${e.message}")

        }
    }

    fun getProductsFamily(onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            val hasMapFamilyProduct: HashMap<String, List<product_stock_response>> = hashMapOf()
            val productsFamily: MutableList<product_stock_response> = mutableListOf()
            try {
                var familyName: String

                val list = apiServices.getProductsFamily()

                for (item in list) {

                    if (item == "") continue

                    val parts = item.toString().split(", separator=null,")
                    val replaced = parts[1].replace("products=[[{", "").replace("]]}", "")
                    val splitted = replaced.split("__v=0.0}")

                    familyName = parts[0].replace("{family=", "").replace(",", "")

                    for (s in splitted) {

                        var _id = ""
                        var name = ""
                        var image = ""
                        var price = 0.0f
                        var stock = 0.0f
                        var sales = 0.0f
                        var wishlists = 0.0f
                        val likes: MutableList<String> = mutableListOf()
                        val families: MutableList<String> = mutableListOf()


                        if (s.isEmpty()) continue
                        val sReplaced = s.replace(", {", "")
                        val familiesRaw = sReplaced.split("families=")
                        val fam = familiesRaw[1].replace("[", "").replace("]", "").split(",")
                        for (i in 0..fam.size - 2) {
                            families.add(fam[i].trim())
                        }


                        val likesRaw = familiesRaw[0].split("likes=")
                        val restAtr = likesRaw[0].split(",")
                        val lik = likesRaw[1].replace("[", "").replace("]", "").split(",")
                        for (i in 0..lik.size - 2) {
                            if (lik[i].trim().isNotEmpty()) likes.add(lik[i].trim())
                        }


                        for (atr in restAtr) {

                            if (atr.contains("=") && atr.isNotEmpty()) {
                                val atrValue = atr.split("=")
                                if (atrValue.size < 2) continue
                                when {
                                    atrValue[0].trim() == "_id" -> _id = atrValue[1]
                                    atrValue[0].trim() == "name" -> name = atrValue[1]
                                    atrValue[0].trim() == "image" -> image = atrValue[1]
                                    atrValue[0].trim() == "price" -> price = atrValue[1].toFloat()
                                    atrValue[0].trim() == "stock" -> stock = atrValue[1].toFloat()
                                    atrValue[0].trim() == "sales" -> sales = atrValue[1].toFloat()
                                    atrValue[0].trim() == "wishlists" -> wishlists = atrValue[1].toFloat()
                                    else -> Logger.i(" *** NEXT PRODUCT *** ")
                                }
                            }
                        }

                        val p = product_stock_response(
                            _id = _id,
                            name = name,
                            image = image,
                            price = price,
                            stock = stock,
                            sales = sales,
                            wishlists = wishlists,
                            likes = likes,
                            families = families
                        )
                        productsFamily.add(p)

                    }
                    hasMapFamilyProduct.put(familyName, productsFamily.toList())
                    productsFamily.clear()
                }
                familyProductsResponse = hasMapFamilyProduct
                Logger.i("SUCCESS getProductsFamily")
                onSuccessCallback()

            } catch (e: Exception) {
                Logger.e("FAILURE getProductsFamily \n $e")
            }
        }
    }

    fun updateFamily(
        newName: String,
        oldName: family_response?,
        onSuccessCallback: () -> Unit
    ) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                if(oldName != null){
                    val response = apiServices.updateFamily(oldName._id, family_response(oldName._id, newName))
                    if (response.isSuccessful){
                        updateOkResponse = true
                        Logger.i("SUCCESS updateTicketLine $response ${response.body()}")
                        onSuccessCallback()
                    }else Logger.e("FAILURE response updateTicketLine ${oldName.name} to $newName")
                }else Logger.e("FAILURE oldName null")

            }catch (e: Exception){
                errorMessage = e.message.toString()
                if (oldName != null) Logger.e("FAILURE update Family ${oldName.name} to $newName\n${e.message.toString()}")
                else Logger.e("FAILURE update Family aldName null\n${e.message.toString()}")
            }
        }

    }

    fun deleteFamily(familySelected: family_response?, onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
               if(familySelected != null){
                   val response = apiServices.deleteFamily(familySelected._id)
                   if (response.isSuccessful){
                       updateOkResponse = true
                       Logger.i("SUCCESS deleteFamily $response ${response.body()}")
                       onSuccessCallback()
                   }else Logger.e("FAILURE response delete Family for ${familySelected.name}")
               }
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE delete Family\n${e.message.toString()}")
            }
        }
    }

    fun updateProductStock(
        newProduct: product_stock_response,
        oldProductId: String,
        onSuccessCallback: () -> Unit
    ) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                    val response = apiServices.updateProductStock(oldProductId = oldProductId, newProduct = newProduct)
                    if (response.isSuccessful){
                        updateOkResponse = true
                        Logger.i("SUCCESS updateProductStock $response ${response.body()}")
                        onSuccessCallback()
                    }else Logger.e("FAILURE response updateProductStock ${newProduct.name} ")

            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE update ProductStock\n${e.message.toString()}")
            }
        }
    }

    fun deleteProductStock(oldProductId: String, onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                    val response = apiServices.deleteProductStock(oldProductId)
                    if (response.isSuccessful){
                        updateOkResponse = true
                        Logger.i("SUCCESS deleteProductStock $response ${response.body()}")
                        onSuccessCallback()
                    }else Logger.e("FAILURE response delete ProductStock for $oldProductId")

            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE delete ProductStock\n${e.message.toString()}")
            }
        }
    }

    fun getOrders(onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try {
                val response : Response<List<order_response>> = apiServices.getOrders()
                if (response.isSuccessful) {
                    updateOkResponse = true
                    Logger.i("SUCCESS getting orders $response ${response.body()}")
                    allOrdersResponse = response.body()!!
                    onSuccessCallback()
                } else Logger.e("FAILURE getting orders response")

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Logger.e("FAILURE getting orders \n${e.message.toString()}")
            }

        }

    }

    fun updateOrder(order: order_response, onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                val response = apiServices.updateOrder(oldOrderId = order._id, newOrder = order)
                if (response.isSuccessful){
                    updateOkResponse = true
                    Logger.i("SUCCESS updateOrder $response ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("FAILURE response updateOrder ${order.user.username} ")

            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE update order\n${e.message.toString()}")
            }
        }
    }

    fun getUserOrders(userId: String, onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            try {
                val response : Response<List<order_response>> = apiServices.getUserOrders(id = userId)
                if (response.isSuccessful) {
                    updateOkResponse = true
                    Logger.i("SUCCESS getting user orders $response ${response.body()}")
                    allOrdersResponse = response.body()!!
                    onSuccessCallback()
                } else Logger.e("FAILURE getting user orders response")

            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Logger.e("FAILURE getting user orders \n${e.message.toString()}")
            }

        }
    }


}