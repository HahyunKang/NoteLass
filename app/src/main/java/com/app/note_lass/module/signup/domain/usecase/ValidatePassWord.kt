package com.app.note_lass.module.signup.domain.usecase

import android.util.Patterns
import java.util.regex.Pattern
import javax.inject.Inject

class ValidatePassWord  @Inject constructor() {
    fun execute(password : String) : ValidationResult{
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=.*[a-zA-Z0-9@#$%^&+=!]).{8,}$"

        if(password == ""){
            return ValidationResult(
                successful = false
            )
        }

        if(!Pattern.matches(passwordPattern,password)){
            return ValidationResult(
                errorMessage = "영문, 숫자, 특수기호 포함 8자리 이상을 입력하세요.",
                successful = false)
        }

        return ValidationResult(
            successful = true
        )


    }


}