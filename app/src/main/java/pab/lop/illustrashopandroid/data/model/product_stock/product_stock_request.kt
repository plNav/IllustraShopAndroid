package pab.lop.illustrashopandroid.data.model.product_stock

data class product_stock_request (
    var name : String,
    var image : String,
    var price : Float,
    var stock : Int,
    var families : List<String>
)