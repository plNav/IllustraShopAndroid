package pab.lop.illustrashopandroid.data.model.user

import java.util.*

data class user_request(
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
)
