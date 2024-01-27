package com.app.note_lass.module.login.data

data class LoginState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isMessage : String = ""
)

data class LogoutState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isMessage : String = ""
)
