package com.app.note_lass.module.login.data

data class LoginDto(
    val token : String ,
    val role : String
)
data class LoginDtoTemp(
    val jwtToken : String
)