package com.app.note_lass.core.Proto

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val accessToken : String?,
    val role : Role

)

enum class Role {
    NONE,TEACHER, STUDENT
}
