package com.app.note_lass.module.group.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.module.upload.data.notice.NoticePreview
import com.app.note_lass.ui.component.IconAndText
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun NoticePreviewScreenForStudent(
    noticeList : List<NoticePreview>,
    onClick : (Long) -> Unit ={}){

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        noticeList.forEach {
            val icon : Int = if(it.unread) R.drawable.notice_preview_unread
            else R.drawable.notice_preview_read
            val iconColor : Color =  if(it.unread) PrimarayBlue
            else PrimaryGray

            IconAndText(
                icon = icon,
                iconColor = iconColor,
                text = it.title,
                onClick = {
                    onClick(it.id)
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}