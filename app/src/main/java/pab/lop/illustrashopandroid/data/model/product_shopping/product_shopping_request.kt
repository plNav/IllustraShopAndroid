package pab.lop.illustrashopandroid.data.model.product_shopping

data class product_shopping_request(
    val id_shopping_cart : String,
    val id_product : String,
    var name : String,
    var image : String,
    var price : Float,
)