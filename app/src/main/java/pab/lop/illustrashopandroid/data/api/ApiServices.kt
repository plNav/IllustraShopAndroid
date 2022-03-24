package pab.lop.illustrashopandroid.data.api

import android.media.Image
import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.data.model.UserModel
import pab.lop.illustrashopandroid.utils.URL_HEAD_API
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

    @GET("images/{name}")
    suspend fun getImage(@Path(value = "image") image : String) : Image








}