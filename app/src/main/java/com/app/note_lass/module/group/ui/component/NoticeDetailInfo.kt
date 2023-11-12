package com.app.note_lass.module.group.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.note_lass.ui.component.Divider
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun NoticeDetailInfo(
    title : String,
    content : String,
    fileUrl : String?
){

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 24.dp, top = 24.dp)) {
        Text(text= title,
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            color = PrimaryBlack

        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider()

        Text(text = content,
            modifier= Modifier.padding(vertical = 45.dp),
            style = NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack
        )
        Divider()


    }

}