package pab.lop.illustrashopandroid.utils

import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response

const val URL_HEAD_API : String = "http://192.168.1.93:8082/api/"
const val URL_HEAD_IMAGES : String = "http://192.168.1.93:8082/api/images/"
const val URL_HEAD_LOCAL : String = "C:\\Users\\pablo\\Desktop\\api\\res\\MonaLisa"

var familyProducts: HashMap<String, List<product_stock_response>> = hashMapOf()