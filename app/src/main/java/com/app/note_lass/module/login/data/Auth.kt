package com.app.note_lass.module.login.data

import com.app.note_lass.core.Proto.Role

data class Auth(
    val token : String,
    val refreshToken : String,
    val role : String
)
