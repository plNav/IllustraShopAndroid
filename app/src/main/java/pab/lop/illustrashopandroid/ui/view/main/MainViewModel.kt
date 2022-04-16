package pab.lop.illustrashopandroid.ui.view.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.orhanobut.logger.Logger
import com.orhanobut.logger.Logger.json
import kotlinx.coroutines.launch
import org.w3c.dom.NodeList
import pab.lop.illustrashopandroid.data.api.ApiServices
import pab.lop.illustrashopandroid.data.model.UserModel
import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response


@SuppressLint("MutableCollectionMutableState")
@Suppress("UNCHECKED_CAST")
class MainViewModel : ViewModel() {

    var allUsersClientResponse: List<UserModel> by mutableStateOf(listOf())
    var familyProductsResponse: HashMap<String, List<product_stock_response>> by mutableStateOf(hashMapOf())


    private var errorMessage: String by mutableStateOf("")


    fun getAllUsers(onSuccessCallback: () -> Unit) {
        viewModelScope.launch {
            val apiServices = ApiServices.getInstance()

            try {
                allUsersClientResponse = apiServices.getAllUsers()
                Logger.i("get all users OK")
                onSuccessCallback()

            } catch (e: Exception) {
                Logger.e("FAILURE getAllUsers \n $e")
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

}