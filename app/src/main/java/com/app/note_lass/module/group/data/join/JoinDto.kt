package com.app.note_lass.module.group.data.join

data class JoinDto(
    val groupId : Long,
    val groupInfo : String,
)

data class JoinStudentInfo(
    val userId : Long,
    val school : String,
    val grade : Int,
    val classNum : Int,
    val name : String
)