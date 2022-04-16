package pab.lop.illustrashopandroid.data.model.product_stock

data class product_stock_response (
    val _id : String,
    var name : String,
    var image : String,
    var price : Float,
    var stock : Float,
    var sales : Float,
    var wishlists : Float,
    var likes : MutableList<String>,
    var families : MutableList<String>
)
