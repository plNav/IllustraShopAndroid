package pab.lop.illustrashopandroid.utils

import androidx.compose.runtime.remember
import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response

const val URL_HEAD_API : String = "http://192.168.1.93:8082/api/"
const val URL_HEAD_IMAGES : String = "http://192.168.1.93:8082/api/images/"
const val URL_HEAD_LOCAL : String = "C:\\Users\\pablo\\Desktop\\api\\res\\MonaLisa"

var familyProducts: HashMap<String, List<product_stock_response>> = hashMapOf()
var excludedFamilies : MutableList<String> = mutableListOf<String>("SecondFamily")
var familyNameList : MutableList<String> = mutableListOf()

val regexSpecialChars = Regex("[^A-Za-z0-9 ]")

val familiesToAssign : MutableList<String> = mutableListOf()


var productSelected : product_stock_response? = null
var familySelected : family_response? = null



