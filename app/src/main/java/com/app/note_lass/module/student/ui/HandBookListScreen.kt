package com.app.note_lass.module.student.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.note_lass.common.StringToDate
import com.app.note_lass.module.student.data.HandBook

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HandBookListScreen(
    handBookList : List<HandBook>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        handBookList.forEach {
            val formatDate =StringToDate(it.createdDate).localDateTime
            HandBookContent(date = formatDate, content = it.content)
        }

    }

}