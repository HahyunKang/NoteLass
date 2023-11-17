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

class RetrievePDFfromUrl(val doAfter: (InputStream) -> Unit) {

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
}