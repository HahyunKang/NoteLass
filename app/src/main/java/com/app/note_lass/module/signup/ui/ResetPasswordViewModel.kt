package com.app.note_lass.module.signup.ui

import android.os.Build
import android.service.voice.VoiceInteractionSession.Request
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.RequestState
import com.app.note_lass.common.Resource
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.signup.data.EmailRequestState
import com.app.note_lass.module.signup.data.EmailValidateState
import com.app.note_lass.module.signup.data.ResetPasswordRequest
import com.app.note_lass.module.signup.data.SchoolName
import com.app.note_lass.module.signup.data.SignUpApiState
import com.app.note_lass.module.signup.data.SignUpRequest
import com.app.note_lass.module.signup.domain.usecase.ValidateEmail
import com.app.note_lass.module.signup.domain.usecase.ValidatePassWord
import com.app.note_lass.module.signup.domain.usecase.ValidateRepeatedPassWord
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormEvent
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormState
import com.app.note_lass.module.signup.domain.usecase.EmailRequestUseCase
import com.app.note_lass.module.signup.domain.usecase.EmailValidateUseCase
import com.app.note_lass.module.signup.domain.usecase.PasswordValidateUseCase
import com.app.note_lass.module.signup.domain.usecase.PostPasswordUseCase
import com.app.note_lass.module.signup.domain.usecase.ResetPasswordUseCase
import com.app.note_lass.module.signup.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassWord: ValidatePassWord,
    private val validateRepeatedPassWord: ValidateRepeatedPassWord,
    private val postPasswordUseCase: PostPasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val passwordValidateUseCase: PasswordValidateUseCase
) :ViewModel() {
   //매번 새로운 객체 생성
    var state by mutableStateOf(RegistrationFormState())

   // var signupState by mutableStateOf(SignupInfo())
   @RequiresApi(Build.VERSION_CODES.O)
   //var signupState =
     ///   mutableStateOf(SignupInfo())

//
//    private val _signUpApiState = mutableStateOf(SignUpApiState())
//    @RequiresApi(Build.VERSION_CODES.O)
//    val signUpApiState = _signUpApiState

    private val _emailRequestState= mutableStateOf(EmailRequestState())
    @RequiresApi(Build.VERSION_CODES.O)
    val emailRequestState = _emailRequestState

    private val _emailValidate = mutableStateOf(EmailValidateState())
    val emailValidateState = _emailValidate

    private val _resetPasswordState = mutableStateOf(RequestState<Nothing>())
    val resetPasswordState = _resetPasswordState
    fun onEvent(event : RegistrationFormEvent){
        when(event){
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
                val emailResult = validateEmail.execute(state.email)
                //유효하면 success
                if (!emailResult.successful)
                    state = state.copy(emailError = emailResult.errorMessage, emailValidationError = "이메일 인증 오류")
                else{
                    state= state.copy(emailError = null, emailValidationError = null)
                    emailValidate(state.email,state.emailValidation)
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
            is RegistrationFormEvent.ValidationChanged -> {
                state = state.copy(emailValidation =  event.validatedNum)
                emailValidate(state.email,state.emailValidation)
            }
            else -> {}
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun emailRequest(email:String, isToast :  () -> Unit ){
       postPasswordUseCase(email).onEach {
                result ->
            when(result)
            {
                is Resource.Success -> {
                    _emailRequestState.value  = EmailRequestState(
                            isSuccess = true,
                            isLoading = false
                    )
                    isToast()
                }

                is Resource.Error-> {
                    _emailRequestState.value  = EmailRequestState(
                        isError = true
                    )

                    if(result.code == 400){
                        state= state.copy(emailError = "가입되지 않은 이메일입니다.")
                    }
                }
                is Resource.Loading -> {
                    _emailRequestState.value  = EmailRequestState(
                        isSuccess = false,
                        isLoading = true
                    )
                }
            }


        }.launchIn(viewModelScope)
    }

    fun emailValidate(email:String,authCode:String, isToast :  (String) -> Unit ={} ){
        passwordValidateUseCase(email,authCode).onEach {
                result ->

            when(result)
            {
                is Resource.Success -> {
                    if(result.data==true) {
                        _emailValidate.value = EmailValidateState(
                            isSuccess = true,
                            isLoading = false
                        )
                    }else{
                        _emailValidate.value = EmailValidateState(
                            isSuccess = false,
                            isLoading = false
                        )
                    }
                    Log.e("signup Success Api", result.message.toString())

                }

                is Resource.Error-> {
                    _emailValidate.value  = EmailValidateState(
                        isError = true
                    )
                    Log.e("signup Error Api", result.message.toString())
                    isToast(result.message.toString())

                }
                is Resource.Loading -> {
                    Log.e("signup Loading Api", result.message.toString())
                    _emailValidate.value  = EmailValidateState(
                        isSuccess = false,
                        isLoading = true
                    )
                }
            }


        }.launchIn(viewModelScope)
    }

    fun resetPassWord(email: String, code : Int, newPassword : String,isToast: (String) -> Unit={}){
        resetPasswordUseCase(ResetPasswordRequest(email,code, newPassword)).onEach {
                result ->
            when(result)
            {
                is Resource.Success -> {
                    if(result.data?.code==200) {
                        _resetPasswordState.value = RequestState(
                            isSuccess = true,
                            isLoading = false
                        )
                    }else{
                        _resetPasswordState.value = RequestState(
                            isSuccess = false,
                            isLoading = false
                        )
                    }
                    Log.e("signup Success Api", result.message.toString())

                }

                is Resource.Error-> {
                    _resetPasswordState.value = RequestState(
                        isError = true
                    )
                    Log.e("signup Error Api", result.message.toString())
                    isToast(result.message.toString())

                }
                is Resource.Loading -> {
                    Log.e("signup Loading Api", result.message.toString())
                    _resetPasswordState.value = RequestState(
                        isSuccess = false,
                        isLoading = true
                    )
                }
            }


        }.launchIn(viewModelScope)

    }
}


