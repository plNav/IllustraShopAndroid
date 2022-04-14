package pab.lop.illustrashopandroid.ui.view_model

import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper.getMainLooper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import pab.lop.illustrashopandroid.data.api.ApiServices
import pab.lop.illustrashopandroid.ui.view.admin.ProgressRequestBody
import retrofit2.await
import java.io.*


class AdminViewModel : ViewModel() {

    private var errorMessage: String by mutableStateOf("")
    private var byteArray: ByteArray? by mutableStateOf(null)

    ///LAST INTENT
    fun multipartImageUpload(
        byteArray: MutableState<ByteArray?>,
        context: Context,
        scope: CoroutineScope,
        bitmap: MutableState<Bitmap?>,
        onSuccess: () -> Unit
    ) {
        val apiService = ApiServices.getInstance()
        Logger.wtf("STEP 0")

        try {
            viewModelScope.launch {
                if (bitmap.value != null) {
                    val bos = ByteArrayOutputStream()
                    bitmap.value?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
                    byteArray.value = bos.toByteArray()
                    Logger.wtf("STEP 1")

                    val filesDir: File = context.filesDir
                    val file = File(filesDir, "image" + ".jpg")
                    val fos = FileOutputStream(file)
                    fos.write(byteArray.value)
                    fos.flush()
                    fos.close()

                    val fileBody = ProgressRequestBody(file)

                    val body: MultipartBody.Part = createFormData("upload", file.name, fileBody)

                    val name = RequestBody.create("text/plain".toMediaTypeOrNull(), "upload")

                    val mainHandler = Handler(getMainLooper())
                    val runnable = Runnable {
                        val asyn = viewModelScope.async {
                            apiService.postImage(image = body, name = name).await()
                        }
                    }

                    mainHandler.postDelayed(runnable, 200)  //delay)

                }
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Logger.wtf("ERROR UPLOAD ${e.message}")

        } catch (e: IOException) {
            e.printStackTrace()
            Logger.wtf("ERROR UPLOAD ${e.message}")

        } catch (e: Exception) {
            e.printStackTrace()
            Logger.wtf("ERROR UPLOAD ${e.message}")

        }
    }


}