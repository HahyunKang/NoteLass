package com.app.note_lass.module.dashboard.data.Material

import com.app.note_lass.common.File

data class Material(
    val id : Long,
    val title :String,
    val content : String,
    val createdDate : String,
    val files : List<File>?
)
