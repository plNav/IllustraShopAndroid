package pab.lop.illustrashopandroid.data.api

import com.orhanobut.logger.Logger
import pab.lop.illustrashopandroid.data.model.UserModel
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

                val address : Array<String> = arrayOf("http://192.168.1.93:8082/api/", "Pablo")

                apiServices = Retrofit.Builder()
                    .baseUrl(address[0])
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiServices::class.java)

                Logger.w("Connected a la API, ${address[0]}; IP de ${address[1]}")
            }
            return apiServices!!
        }
    }


    /*********************RUTAS**********************/

    @Headers("Accept: Application/json")





    /********************TABLES**********************/

    @GET("user")
    suspend fun getAllUsers() : List<UserModel>






}