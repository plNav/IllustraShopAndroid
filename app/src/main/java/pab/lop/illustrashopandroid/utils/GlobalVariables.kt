package pab.lop.illustrashopandroid.utils

import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.data.model.shopping_cart.shopping_cart_response
import pab.lop.illustrashopandroid.data.model.user.user_response
import java.util.*
import kotlin.collections.HashMap

const val URL_HEAD_API : String = "http://192.168.1.93:8082/api/"
const val URL_HEAD_IMAGES : String = "http://192.168.1.93:8082/api/images/"

const val TEST_BANNER : String = "ca-app-pub-3940256099942544/6300978111"
//const val TEST_ADAPTIVE_BANNER : String = "ca-app-pub-3940256099942544~3347511713"
const val TEST_INTERSTITIAL : String = "ca-app-pub-3940256099942544/1033173712"

const val PENDING = "PENDING"
const val SENT = "SENT"
const val ENDED = "ENDED"

var familyProducts: HashMap<String, List<product_stock_response>> = hashMapOf()
var excludedFamilies : MutableList<String> = mutableListOf("SecondFamily")
var familyNameList : MutableList<String> = mutableListOf()

val regexSpecialChars = Regex("[^A-Za-z0-9 ]")

var productSelected : product_stock_response? = null
var familySelected : family_response? = null
var userSelected : user_response? = null
var shoppingCartSelected : shopping_cart_response? = null
var currentShoppingProducts : MutableList<product_shopping_response> = mutableListOf()
var allOrders : MutableList<order_response> = mutableListOf()
var userOrders : MutableList<order_response> = mutableListOf()
var wishlistProducts : MutableList<product_stock_response> = mutableListOf()

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
    total_spent = 0.0f,
    register_count = 0.0f,
    verified_buys = listOf(),
    wishlist = mutableListOf(),
    first_register = Date(),
    last_register = Date(),
    google = false
)

val shoppingCartDefaultNoAuth = shopping_cart_response(
    _id = "",
    id_user = "",
    discount = "",
    pay_method = "",
    comment = ""
)

