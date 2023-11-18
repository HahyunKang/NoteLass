package com.app.note_lass.module.student.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.note_lass.common.DateFormatter
import com.app.note_lass.ui.theme.NoteLassTheme
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun HandBookContent(
    date : LocalDateTime,
    content : String
){
    val formatDate = DateFormatter(date).formattedDate

    Column(verticalArrangement = Arrangement.Center) {
        Text(text = formatDate,
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = content,
            style = NoteLassTheme.Typography.fourteen_600_pretendard,
            color = Color.Black,
            modifier = Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
        )

    }
}

