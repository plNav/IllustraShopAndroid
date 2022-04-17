package pab.lop.illustrashopandroid.data.api

import android.media.Image
import com.orhanobut.logger.Logger
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pab.lop.illustrashopandroid.data.model.UserModel
import pab.lop.illustrashopandroid.data.model.family.family_request
import pab.lop.illustrashopandroid.data.model.family.family_response
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_request
import pab.lop.illustrashopandroid.data.model.product_stock.product_stock_response
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





    /********************TABLES**********************/

    @GET("user")
    suspend fun getAllUsers() : List<UserModel>


    /********************IMAGES**********************/

    //todo creo que no hace falta
    @GET("image/{name}")
    suspend fun getImage(@Path(value = "image") image : String) : Image


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

    /********************PRODUCT_STOCK**********************/

    @GET("product/stock")
    suspend fun getProducts(): Response<List<product_stock_response>>

    @POST("product/stock")
    suspend fun createProductStock(@Body newProduct: product_stock_request): Response<product_stock_response>


}