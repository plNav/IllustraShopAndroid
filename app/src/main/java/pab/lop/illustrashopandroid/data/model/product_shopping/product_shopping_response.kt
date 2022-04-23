package pab.lop.illustrashopandroid.data.model.product_shopping

data class product_shopping_response(
    val _id : String,
    val id_shopping_cart : String,
    val id_product : String,
    val name : String,
    val image : String,
    val price : Float,
    var total : Float,
    var amount : Float,
    val comments : String
)