package pab.lop.illustrashopandroid.data.api

import android.media.Image
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pab.lop.illustrashopandroid.data.model.user.user_response
import pab.lop.illustrashopandroid.data.model.family.family_request
import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.order.order_request
import pab.lop.illustrashopandroid.data.model.order.order_response
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_request
import pab.lop.illustrashopandroid.data.model.product_shopping.product_shopping_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_request
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
import pab.lop.illustrashopandroid.data.model.shoppin.shopping_cart_request
import pab.lop.illustrashopandroid.data.model.shopping_cart.shopping_cart_response
import pab.lop.illustrashopandroid.data.model.user.user_request
import pab.lop.illustrashopandroid.utils.URL_HEAD_API
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


/**
 * Interfaz que contiene las rutas para llamar a la API y la instancia de Retrofit para hacerlo
 * se tiene que llamar desde el ViewModel
 */
interface ApiServices {

    companion object {
        private var apiServices: ApiServices? = null

        fun getInstance(): ApiServices {
            if(apiServices == null){

                apiServices = Retrofit.Builder()
                    .baseUrl(URL_HEAD_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServices::class.java)

                Logger.w("Connected a la API, $URL_HEAD_API")
            }
            return apiServices!!
        }
    }


    /*********************ROUTES**********************/

    @Headers("Accept: Application/json")





    /********************USER**********************/

    @GET("user")
    suspend fun getAllUsers() : List<user_response>

    @GET("user/validate/{email}/{passwEncrypt}")
    suspend fun validateClient (
        @Path("email") email : String,
        @Path("passwEncrypt") passwEncrypt: String
    ) : Response<List<user_response>>


    @GET("user/get/username")
    suspend fun getAllUsernames() : Response<List<String>>

    @GET("user/get/email")
    suspend fun getAllEmail() : Response<List<String>>

    @POST("user")
    suspend fun createUser(@Body newUser: user_request): Response<user_response>



    /********************IMAGES**********************/

    @Multipart
    @POST("images/upload")
    fun postImage(
        @Part image: MultipartBody.Part?,
        @Part("upload") name: RequestBody?
    ) : Call<Any>

    /********************FAMILY+PRODUCTS**********************/

    @GET("product/family")
    suspend fun getProductsFamily() : List<Any>

    /********************FAMILY**********************/

    @POST("family")
    suspend fun createFamily(@Body family : family_request) : Response<family_response>

    @GET("family/names/all")
    suspend fun getFamilyNames() : Response<List<String>>

    @GET("family")
    suspend fun getFamilies(): Response<List<family_response>>

    @PUT("family/{id}")
    suspend fun updateFamily(@Path(value = "id" ) id : String, @Body newFamily: family_response): Response<Any>

    @DELETE("family/{id}")
    suspend fun deleteFamily(@Path(value = "id") id : String) : Response<Any>


    /********************PRODUCT_STOCK**********************/

    @GET("product/stock")
    suspend fun getProducts(): Response<List<product_stock_response>>

    @POST("product/stock")
    suspend fun createProductStock(@Body newProduct: product_stock_request): Response<product_stock_response>

    @PUT("product/stock/{id}")
    suspend fun updateProductStock(@Path(value = "id" ) oldProductId: String, @Body newProduct: product_stock_response): Response<Any>

    @DELETE("product/stock/{id}")
    suspend fun deleteProductStock(@Path(value = "id" )oldProductId: String): Response<Any>


    /********************SHOPPING_CART**********************/

    @GET("shopping_cart/user/{id_user}")
    suspend fun getShoppingCart(@Path(value = "id_user") id_user : String): Response<shopping_cart_response>

    @POST("shopping_cart")
    suspend fun createShoppingCart(@Body newShoppingCart : shopping_cart_request) : Response<shopping_cart_response>


    /********************PRODUCT_SHOPPING**********************/

    @GET("product/shopping/cart/{id_cart}")
    suspend fun getAllProductsFromShoppingCart(@Path(value = "id_cart") id_cart : String) : Response<List<product_shopping_response>>

    @POST("product/shopping")
    suspend fun createProductShopping(@Body newProductShopping : product_shopping_request) : Response<product_shopping_response>

    @PUT("product/shopping/{id}")
    suspend fun updateProductShopping(@Path(value = "id") product_id: String, @Body product: product_shopping_response): Response<Any>

    @DELETE("product/shopping/{id}")
    suspend fun deleteProductShopping(@Path(value = "id" ) id: String): Response<Any>



    /********************ORDER**********************/
    @GET("order")
    suspend fun getOrders(): Response<List<order_response>>

    @POST("order")
    suspend fun createOrder(@Body order : order_request) : Response<Any>


}