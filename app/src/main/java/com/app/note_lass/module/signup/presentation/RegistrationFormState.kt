package com.app.note_lass.module.signup.presentation

data class RegistrationFormState(
    val email : String = "",
    val emailError : String? = null,
    val emailValidation : String = "",
    val emailValidationError : String? = null,
    val password : String  = "",
    val passwordError : String? = null,
    val repeatedPassword : String  = "",
    val repeatedPasswordError : String ? = null
)
