package com.app.note_lass.module.group.data.upload.notice

data class Notice(
    val id : Long,
    val groupId : Long,
    val title : String,
    val content : String,
    val teacher : String,
    val createdDate : String,
    val fileUrl : String?,
    val unread : Boolean,
)

data class NoticePreview(
    val id : Long,
    val title : String,
    val unread: Boolean,
)
fun Notice.toPreview(): NoticePreview {
    return NoticePreview(id, title, unread)
}
