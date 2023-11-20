package com.app.note_lass.module.upload.data.notice

import java.time.LocalDateTime

data class Notice(
    val id : Long,
    val groupId : Long,
    val title : String,
    val content : String,
    val teacher : String,
    val createdDate : LocalDateTime,
    val fileUrl : String?,
    val unread : Boolean,
)

data class NoticePreview(
    val id : Long,
    val title : String,
    val unread: Boolean,
)

data class NoticeDetail(
    val title : String,
    val content : String,
    val fileUrl : String?,
    val createdDate: LocalDateTime?
)
fun Notice.toPreview(): NoticePreview {
    return NoticePreview(id, title, unread)
}

fun Notice.toDetail() : NoticeDetail {
    return NoticeDetail(title,content, fileUrl,createdDate)
}
