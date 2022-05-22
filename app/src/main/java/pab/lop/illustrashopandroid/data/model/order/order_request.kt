package pab.lop.illustrashopandroid.data.model.order

import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.data.model.user.user_response

data class order_request(
    var user: user_response,
    var products: List<product_shopping_response>,
    var total: Float,
    var status: String,
    var comments: String
)