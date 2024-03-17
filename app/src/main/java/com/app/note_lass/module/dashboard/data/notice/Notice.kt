package com.app.note_lass.module.dashboard.data.notice

import com.app.note_lass.common.File

data class Notice(
    val id : Long,
    val groupId : Long,
    val title : String,
    val content : String,
    val teacher : String,
    val createdDate : String?,
    val files : List<File>?,
    val unread : Boolean,
)
data class Materials(
    val id : Long,
    val groupId : Long,
    val title : String,
    val content : String,
    val teacher : String,
    val createdDate : String?,
    val files : List<File>?,
    val unread : Boolean,
)
data class NoticePreview(
    val id : Long,
    val title : String,
    val unread: Boolean,
)

data class NoticeDetail(
    val title: String,
    val teacher : String,
    val content: String,
    val file: List<File>?,
    val createdDate: String?
)


data class MaterialDetail(
    val title: String,
    val content: String,
    val file: List<File>?,
    val createdDate: String?
)
fun Notice.toPreview(): NoticePreview {
    return NoticePreview(id, title, unread)
}

fun Notice.toDetail() : NoticeDetail {
    return NoticeDetail(title, teacher,content,files ,createdDate)
}

fun Materials.toDetail() : MaterialDetail {
    return MaterialDetail(title,content, files ,createdDate)
}
