package com.app.note_lass.module.group.data.upload.notice

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime


data class NoticeListState(
val isLoading : Boolean = false,
val isSuccess : Boolean = false,
val isError : Boolean = false,
val noticeList : List<NoticePreview> = emptyList(),
val isMessage : String = ""
)

data class NoticeDetailState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val noticeDetail: NoticeDetail = NoticeDetail("","",null,null),
    val isMessage : String = ""
)
