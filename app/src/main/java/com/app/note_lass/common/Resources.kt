package com.app.note_lass.common

data class Resources(
    val id : Long,
    val groupId : Long,
    val title : String,
    val content : String,
    val teacher : String,
    val createdDate : String?,
    val fileUrl : String?,
    val unread : Boolean,
)
