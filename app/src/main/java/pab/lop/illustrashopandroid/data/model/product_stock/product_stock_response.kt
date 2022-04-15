package pab.lop.illustrashopandroid.data.model.product_stock

data class product_stock_response (
    val _id : String,
    var name : String,
    var image : String,
    var price : Float,
    var stock : Int,
    var sales : Int,
    var whislists : Int,
    var likes : List<String>,
    var families : List<String>
)
