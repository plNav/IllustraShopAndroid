package pab.lop.illustrashopandroid.ui.view.main

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch
import pab.lop.illustrashopandroid.data.api.ApiServices
import pab.lop.illustrashopandroid.data.model.order.order_request
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_request
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.data.model.user.user_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.data.model.shopping_cart.shopping_cart_response
import retrofit2.Response


@SuppressLint("MutableCollectionMutableState")
@Suppress("UNCHECKED_CAST")
class MainViewModel : ViewModel() {

    var familyProductsResponse: HashMap<String, List<product_stock_response>> by mutableStateOf(hashMapOf())
    var currentShoppingCartResponse: shopping_cart_response? by mutableStateOf(null)
    var currentProductsShopping : MutableList<product_shopping_response> by mutableStateOf(mutableListOf())
    var updateOkResponse : Boolean by mutableStateOf(false)
    var currentProductShoppingResponse : product_shopping_response? by mutableStateOf(null)
    var productListResponse : MutableList<product_stock_response> by mutableStateOf(mutableListOf())
    var currentPayPalresponse : String by mutableStateOf("")
    private var errorMessage: String by mutableStateOf("")


    fun getAllProductStock(productList : MutableList<String>, onSuccessCallback: () -> Unit){
           val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try {
              //  val products = productList.joinToString("-")
              //  Logger.i(products)
                val response: Response<List<product_stock_response>> =
                    apiServices.getProductsWish(products = productList as ArrayList<String>)
                if (response.isSuccessful) {
                    Logger.i("Get products List ok \n${response.body().toString()}")
                    productListResponse = response.body() as MutableList<product_stock_response>
                    onSuccessCallback()
                } else Logger.e("ErrorGetAllProductWish response \n${response.body().toString()}")
            }catch (e : Exception){
                Logger.e("Error get all products wish \n ${e.message}")
            }
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
                    val split = replaced.split("__v=0.0}")

                    familyName = parts[0].replace("{family=", "").replace(",", "")

                    for (s in split) {

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
                onSuccessCallback()
            }
        }
    }

    fun getShoppingCart(id_user: String, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        try {
            viewModelScope.launch {
                val response: Response<shopping_cart_response> = apiServices.getShoppingCart(id_user)
                if (response.isSuccessful) {
                    Logger.i("Get ShoppingCart \n${response.body().toString()}")
                    currentShoppingCartResponse = response.body()
                    onSuccessCallback()
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
            Logger.e("FAILURE getting shoppingCarts")
        }

    }

    fun createProductShopping(newProduct: product_shopping_request, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try {
                val response: Response<product_shopping_response> = apiServices.createProductShopping(newProduct)
                if (response.isSuccessful) {
                    Logger.i("Create product shopping OK \n $response \n ${response.body()}")
                    currentProductShoppingResponse = response.body()!!
                    onSuccessCallback()
                } else Logger.e("Error product shopping creation $response")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Logger.e("FAILURE create shopping product")
            }

        }

    }

    fun getAllProductShopping(id_cart: String, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        try {
            viewModelScope.launch {
                val response: Response<List<product_shopping_response>> = apiServices.getAllProductsFromShoppingCart(id_cart)
                if (response.isSuccessful) {
                    Logger.i("all product shopping ok \n${response.body().toString()}")
                    currentProductsShopping = mutableListOf()
                    currentProductsShopping = (response.body() as MutableList<product_shopping_response>?)!!
                    onSuccessCallback()
                } else Logger.e("Error get all product shopping $response")
            }
        } catch (e: Exception) {
            errorMessage = e.message.toString()
            Logger.e("FAILURE getting all product shopping")
        }
    }

    fun updateProductShopping(product: product_shopping_response, onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                product.total = product.amount * product.price
                val response = apiServices.updateProductShopping(product._id, product)
                if (response.isSuccessful){
                    updateOkResponse = true
                    Logger.i("SUCCESS updateProductShopping \n $response ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("FAILURE response update product Shopping ")
            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE updatee product Shopping\n${e.message.toString()}")
            }
        }
    }

    fun markBoughtProducts(products: List<product_shopping_response>, onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()
            updateOkResponse = false
            try{
                for(product in products){
                    if(!product.bought){
                        product.bought = true
                        val response = apiServices.updateProductShopping(product._id, product)
                        if (response.isSuccessful){
                            updateOkResponse = true
                            Logger.i("SUCCESS updateProductShopping \n $response ${response.body()}")
                        }else Logger.e("FAILURE response update product Shopping ")
                    }
                }
                Logger.i("Update all products bought ok")
                onSuccessCallback()

            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE updatee product Shopping\n${e.message.toString()}")
            }
        }
    }

    fun createOrder(order: order_request, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try {
                Logger.i(order.status)
                order.status = "UNPAID"
                val response: Response<Any> = apiServices.createOrder(
                    pay = order.total,
                    order = order
                )
                if (response.isSuccessful) {
                    currentPayPalresponse = response.body().toString()
                    Logger.i("Create order OK \n $response \n ${response.body()}")
                    onSuccessCallback()
                } else Logger.e("Error order creation response $response")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Logger.e("FAILURE create order")
            }

        }
    }


    fun deleteProductSelected(id: String, onSuccessCallback: () -> Unit) {

        val apiServices = ApiServices.getInstance()
        viewModelScope.launch {
            try {
                val response: Response<Any> = apiServices.deleteProductShopping(id)
                if (response.isSuccessful) {
                    Logger.i("delete product shoping OK \n $response \n ${response.body()}")
                    onSuccessCallback()
                } else Logger.e("Error delete product shoping response $response")
            } catch (e: Exception) {
                errorMessage = e.message.toString()
                Logger.e("FAILURE delete product shoping")
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
                    Logger.i("SUCCESS updateuser $response ${response.body()}")
                    onSuccessCallback()
                }else Logger.e("FAILURE response updateUser $user ")

            }catch (e: Exception){
                errorMessage = e.message.toString()
                Logger.e("FAILURE update user\n${e.message.toString()}")
            }
        }
    }

}