package com.app.note_lass.module.dashboard.data.notice


data class NoticeListState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val noticeList : List<NoticePreview> = emptyList(),
    val isMessage : String = ""
)

data class NoticeDetailState (
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val noticeDetail: NoticeDetail = NoticeDetail("","","",null,null),
    val isMessage : String = ""
)
