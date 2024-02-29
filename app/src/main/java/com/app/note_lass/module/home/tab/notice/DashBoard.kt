package com.app.note_lass.module.home.tab.notice

data class DashBoard(
    val content: String,
    val createdDate: String,
    val lectureMaterialId: Long?,
    val noticeId: Long?,
    val teacherName: String?,
    val title: String
)

