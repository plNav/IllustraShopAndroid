package pab.lop.illustrashopandroid.ui.view.admin

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ProgressRequestBody(private val mFile: File, /*private val mListener: UploadCallbacks*/) :
    RequestBody() {
    interface UploadCallbacks {
        fun onProgressUpdate(percentage: Int)
        fun onError()
        fun onFinish()
        fun uploadStart()
    }

    override fun contentType(): MediaType? {
        // i want to upload only images
        return "image/*".toMediaTypeOrNull()
    }

   // @kotlin.Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile.length()
    }

  //  @kotlin.Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        val fileLength = mFile.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val `in` = FileInputStream(mFile)
        var uploaded: Long = 0
        try {
            var read: Int = 0
            val handler = Handler(Looper.getMainLooper())
            while (`in`.read(buffer).also { read = it } != -1) {
                uploaded += read.toLong()
                sink.write(buffer, 0, read)
                //handler.post(ProgressUpdater(uploaded, fileLength))
            }
        } finally {
            `in`.close()
        }
    }

/*    private inner class ProgressUpdater(private val mUploaded: Long, private val mTotal: Long) :
        Runnable {
        override fun run() {
            try {
                val progress = (100 * mUploaded / mTotal).toInt()
                if (progress == 100) mListener.onFinish() else mListener.onProgressUpdate(progress)
            } catch (e: ArithmeticException) {
                mListener.onError()
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }

    init {
        mListener.uploadStart()
    }*/
}