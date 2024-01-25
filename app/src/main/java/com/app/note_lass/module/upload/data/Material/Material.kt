package com.app.note_lass.module.upload.data.Material

import java.time.LocalDateTime

data class Material(
    val id : Long,
    val title :String,
    val content : String,
    val createdDate : String,
    val fileUrl : String
)
