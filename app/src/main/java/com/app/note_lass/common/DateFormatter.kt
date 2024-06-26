package com.app.note_lass.common

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateFormatter(val localDateTime: LocalDateTime) {

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd a h:mm", Locale.KOREA)

    @RequiresApi(Build.VERSION_CODES.O)
    val formattedDateTime = localDateTime.format(formatter)

    @RequiresApi(Build.VERSION_CODES.O)
    val formatterNoTime = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)

    @RequiresApi(Build.VERSION_CODES.O)
    val formattedDate = localDateTime.format(formatterNoTime)

    @RequiresApi(Build.VERSION_CODES.O)
    fun printDate(){
        Log.e("printDate",formattedDateTime)
    }

}
class StringToDate(val localString : String) {
    @RequiresApi(Build.VERSION_CODES.O)
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val localStrings =localString.take(19)
    @RequiresApi(Build.VERSION_CODES.O)
    val localDateTime = LocalDateTime.parse(localStrings, formatter)

}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PrintPreviewDate(){
    val dataFormatter = DateFormatter(LocalDateTime.now())
    dataFormatter.printDate()
}





