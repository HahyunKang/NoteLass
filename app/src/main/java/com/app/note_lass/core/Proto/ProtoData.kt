package com.app.note_lass.core.Proto

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val accessToken : String?,
    val refreshToken : String?,
    val role : Role

)

enum class Role {
    NONE,TEACHER, STUDENT
}


@Serializable
data class GroupInfo(
    val groupName : String?,
    val teacherName : String?,
    val groupId : Long?
)