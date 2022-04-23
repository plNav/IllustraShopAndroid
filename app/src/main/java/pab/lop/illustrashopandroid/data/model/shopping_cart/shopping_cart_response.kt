package pab.lop.illustrashopandroid.data.model.shopping_cart

data class shopping_cart_response (
    val _id : String,
    val id_user : String,
    var discount : String,
    var pay_method : String,
    var comment : String
)
