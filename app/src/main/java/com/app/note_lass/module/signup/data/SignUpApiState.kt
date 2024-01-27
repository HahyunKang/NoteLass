package com.app.note_lass.module.signup.data

data class SignUpApiState(
    var isSuccess : Boolean = false,
    var isError : Boolean = false,
    var isLoading : Boolean = false,
    )
data class EmailRequestState(
    var isSuccess : Boolean = false,
    var isError : Boolean = false,
    var isLoading : Boolean = false,
)
data class EmailValidateState(
    var isSuccess : Boolean = false,
    var isError : Boolean = false,
    var isLoading : Boolean = false,
)