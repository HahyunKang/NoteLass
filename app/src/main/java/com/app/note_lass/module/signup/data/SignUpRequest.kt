package com.app.note_lass.module.signup.data

data class SignUpRequest(
    val email: String,
    val password: String,
    val grade: Int?,
    val classNum: Int?,
    val number: Int?,
    val name: String,
    val admissionYear: String,
    val school: String,
    val role: String,
)