package com.app.note_lass.module.group.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import java.lang.Integer.min

@Composable
fun NoticePreviewScreenForStudent(
    noticeList : List<NoticePreview>,
    onClick : (Long) -> Unit ={}){

    val list = if(noticeList.isNotEmpty())noticeList.reversed().subList(0,min(noticeList.size,4))
    else emptyList()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        list.forEach {
            val icon : Int = if(it.unread) R.drawable.notice_preview_unread
            else R.drawable.notice_preview_read
            val iconColor : Color =  if(it.unread) PrimarayBlue
            else PrimaryGray
            Box(
                modifier = Modifier.weight(1f)
            ) {
                IconAndText(
                    icon = icon,
                    iconColor = iconColor,
                    text = it.title,
                    onClick = {
                        onClick(it.id)
                    },
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}