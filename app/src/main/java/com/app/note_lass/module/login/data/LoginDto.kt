package com.app.note_lass.module.login.data

data class LoginDto(
    val result : String ,
    val code : Int,
    val message : String,
)
data class LoginDtoTemp(
    val jwtToken : String
)