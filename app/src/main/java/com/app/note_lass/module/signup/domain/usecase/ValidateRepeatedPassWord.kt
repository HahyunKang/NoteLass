package com.app.note_lass.module.signup.domain.usecase

import android.util.Patterns
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateRepeatedPassWord  @Inject constructor(){
    fun execute(password : String, repeatedPassWord: String) : ValidationResult{

        if(repeatedPassWord == ""){
            return ValidationResult(
                successful = false
            )
        }
        if(password!= repeatedPassWord){
            return ValidationResult(
                errorMessage = "비밀번호가 일치하지 않습니다",
                successful = false)
        }

        return ValidationResult(
            successful = true
        )


    }


}