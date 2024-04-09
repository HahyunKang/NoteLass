package com.app.note_lass.module.group.data.upload.notice


data class NoticeListState(
val isLoading : Boolean = false,
val isSuccess : Boolean = false,
val isError : Boolean = false,
val noticeList : List<NoticePreview> = emptyList(),
val isMessage : String = ""
)

data class NoticeDetailState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val noticeDetail : Notice = Notice(0,0,"","","","",null,false),
    val isMessage : String = ""
)