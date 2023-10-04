package com.app.note_lass.module.signup.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.app.note_lass.module.signup.domain.usecase.ValidateEmail
import com.app.note_lass.module.signup.domain.usecase.ValidatePassWord
import com.app.note_lass.module.signup.domain.usecase.ValidateRepeatedPassWord
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormEvent
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class  SignUpViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassWord: ValidatePassWord,
    private val validateRepeatedPassWord: ValidateRepeatedPassWord
) : ViewModel(){

    var state by mutableStateOf(RegistrationFormState())

    fun onEvent(event : RegistrationFormEvent){
        when(event){
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
                val emailResult = validateEmail.execute(state.email)
                //유효하면 success
                if (!emailResult.successful) state =
                    state.copy(emailError = emailResult.errorMessage)
                else{
                    state= state.copy(emailError = null)
                }
            }
            is RegistrationFormEvent.PassWordChanged ->{
                state = state.copy(password = event.password)
                val passwordResult = validatePassWord.execute(state.password)
                if(!passwordResult.successful)state =
                    state.copy(passwordError = passwordResult.errorMessage)
                else{
                    state = state.copy(passwordError = null)
                }
            }
            is RegistrationFormEvent.RepeatedPassWordChanged ->{
                state = state.copy(repeatedPassword = event.repeatedPassword)
                val repeatedPassWordResult = validateRepeatedPassWord.execute(state.password,state.repeatedPassword)
                if(!repeatedPassWordResult.successful)state=
                    state.copy(repeatedPasswordError = repeatedPassWordResult.errorMessage)
                else{
                    state= state.copy(repeatedPasswordError = null)
                }
            }
            is RegistrationFormEvent.Submit -> {

            }
        }


    }


}