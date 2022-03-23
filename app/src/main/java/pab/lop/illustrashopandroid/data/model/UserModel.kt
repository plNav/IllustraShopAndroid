package pab.lop.illustrashopandroid.data.model

import java.util.*

data class UserModel(
    var _id : String,
    var name : String,
    var last_name : String,
    var username : String,
    var email : String,
    var password : String,
    var rol : String,
    var address : String,
    var country : String,
    var postal_code : String,
    var phone : String,
    var pay_method : String,
    var pay_number : String,
    var shopping_card_id : String,
    var total_spent : Float,
    var register_count : Int,
    var verified_buys : List<String>,
    var wishlist : List<String>,
    var first_register : Date,
    var last_register : Date
    )
