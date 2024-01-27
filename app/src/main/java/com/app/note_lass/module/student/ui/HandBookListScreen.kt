package com.app.note_lass.module.student.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.note_lass.common.StringToDate
import com.app.note_lass.module.student.data.HandBook
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HandBookListScreen(
    handBookList : List<HandBook>,
    isMemoActive : Boolean,
    getHandBookList : (List<Int>) -> Unit
) {
    val scrollState = rememberScrollState()
    val idList = remember {
        mutableStateOf(mutableListOf<Int>())
    }
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Text(
            text = "학생 수첩",
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            color = PrimaryBlack,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        handBookList.forEach { handbook ->
            val formatDate = StringToDate(handbook.createdDate).localDateTime
            HandBookContent(
                date = formatDate,
                content = handbook.content,
                isMemoActive = isMemoActive,
                isChecked = {
                    if (!it) idList.value.remove(handbook.id)
                    else idList.value.add(handbook.id)
                    getHandBookList(idList.value)
                    Log.e("학생수첩 test", idList.value.size.toString())

                }
            )
            Spacer(modifier = Modifier.height(5.dp))

        }

    }
}