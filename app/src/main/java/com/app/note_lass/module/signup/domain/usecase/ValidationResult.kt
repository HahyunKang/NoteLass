package com.app.note_lass.module.signup.domain.usecase

data class ValidationResult(
    val successful : Boolean,
    val errorMessage : String?= null
)
