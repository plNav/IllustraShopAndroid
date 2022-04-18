package pab.lop.illustrashopandroid.utils

import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.data.model.user.user_response
import java.util.*
import kotlin.collections.HashMap

const val URL_HEAD_API : String = "http://192.168.1.93:8082/api/"
const val URL_HEAD_IMAGES : String = "http://192.168.1.93:8082/api/images/"

var familyProducts: HashMap<String, List<product_stock_response>> = hashMapOf()
var excludedFamilies : MutableList<String> = mutableListOf("SecondFamily")
var familyNameList : MutableList<String> = mutableListOf()

val regexSpecialChars = Regex("[^A-Za-z0-9 ]")

var productSelected : product_stock_response? = null
var familySelected : family_response? = null
var userSelected : user_response? = null

val userDefaultNoAuth = user_response(
    _id = "",
    name = "",
    last_name = "",
    username =  "",
    email = "",
    password = "",
    rol = "",
    address = "",
    country = "",
    postal_code = "",
    phone = "",
    pay_method = "",
    pay_number = "",
    shopping_card_id = "",
    total_spent = 0.0f,
    register_count = 0.0f,
    verified_buys = listOf(),
    wishlist = listOf(),
    first_register = Date(),
    last_register = Date()
)

