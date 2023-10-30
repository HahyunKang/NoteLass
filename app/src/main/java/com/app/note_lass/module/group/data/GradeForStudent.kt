package com.app.note_lass.module.group.data

data class GradeForStudent(
    val grade : String,
    val studentName: String
)
data class InfoForCreate(
    val grade : Int,
    val classNum : Int,
    val subject: String
)

