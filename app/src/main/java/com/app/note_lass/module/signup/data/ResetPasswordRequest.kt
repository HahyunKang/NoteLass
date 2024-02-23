package com.app.note_lass.module.signup.data

data class ResetPasswordRequest(
    val email: String,
    val code : Int,
    val newPassword : String
)
