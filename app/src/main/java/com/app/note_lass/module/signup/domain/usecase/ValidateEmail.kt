package com.app.note_lass.module.signup.domain.usecase

import android.util.Patterns
import javax.inject.Inject

class ValidateEmail @Inject constructor() {
    fun execute(email : String) : ValidationResult{
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "올바른 이메일 주소를 입력해주세요")
        }

        return ValidationResult(
            successful = true
        )


    }


}