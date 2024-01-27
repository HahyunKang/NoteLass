package com.app.note_lass.module.signup.domain.presentation

sealed class RegistrationFormEvent{

    data class EmailChanged(val email : String) : RegistrationFormEvent()

    data class PassWordChanged(val password : String) : RegistrationFormEvent()

    data class RepeatedPassWordChanged(val repeatedPassword : String) : RegistrationFormEvent()
    data class ValidationChanged(val validatedNum : String) : RegistrationFormEvent()

    object Submit : RegistrationFormEvent()




}
