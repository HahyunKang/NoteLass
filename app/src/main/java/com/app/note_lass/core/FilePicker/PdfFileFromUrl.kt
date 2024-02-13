import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RetrievePDFfromUrl(val doAfter: (InputStream) -> Unit = {}) {

    @OptIn(DelicateCoroutinesApi::class)
    fun execute(urlString: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                Log.e("success in background", urlString)

                val url = URL(urlString)
                val urlConnection = url.openConnection() as HttpsURLConnection

                if (urlConnection.responseCode == HttpsURLConnection.HTTP_OK) {
                    Log.e("success in background", "?")

                    val inputStream = BufferedInputStream(urlConnection.inputStream)
                    withContext(Dispatchers.Main) {
                        doAfter(inputStream)
                    }
                }else{
                    Log.e("error in background", urlConnection.responseCode.toString())

                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("error in background", e.message.toString())
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun addPdf(context: Context, uri: Uri?, inputStream: InputStream) {

        GlobalScope.launch(Dispatchers.IO) {
            try {

//                val url = URL(pdfUri)
//                val urlConnection = url.openConnection() as HttpsURLConnection
//
//                if (urlConnection.responseCode == HttpsURLConnection.HTTP_OK) {
                    Log.e("success in background", "?")

                    //Android 장치의 내부 저장소에서 파일을 쓰기 위한 OutputStream을 열기.
                    // 이 OutputStream은 나중에 웹에서 다운로드한 데이터를 장치에 저장하는 데 사용.
                    val outputStream = context.contentResolver.openOutputStream(uri!!)

                    val buffer = ByteArray(8192)
                    var len: Int
                    while (inputStream.read(buffer).also { len = it ?: 0 } != -1) {
                        outputStream?.write(buffer, 0, len)
                    }
                    //버퍼에 저장된 데이터를 OutputStream을 통해 파일에 씁니다
                  inputStream.close()
                    outputStream?.close()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }



}
