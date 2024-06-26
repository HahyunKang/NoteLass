package com.app.note_lass.module.signup.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.module.signup.data.EmailRequestState
import com.app.note_lass.module.signup.data.EmailValidateState
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
class AuthSharedViewModel @Inject constructor(
    private val validateEmail: ValidateEmail,
    private val validatePassWord: ValidatePassWord,
    private val validateRepeatedPassWord: ValidateRepeatedPassWord,
    private val postSignUp : SignUpUseCase,
    private val emailRequestUseCase: EmailRequestUseCase,
    private val emailValidateUseCase: EmailValidateUseCase
) :ViewModel() {
   //매번 새로운 객체 생성
    var state by mutableStateOf(RegistrationFormState())

   // var signupState by mutableStateOf(SignupInfo())
   @RequiresApi(Build.VERSION_CODES.O)
   var signupState =
        mutableStateOf(SignupInfo())

    private val _signUpApiState = mutableStateOf(SignUpApiState())
    val signUpApiState = _signUpApiState

    private val _emailRequestState= mutableStateOf(EmailRequestState())
    val emailRequestState = _emailRequestState

    private val _emailValidate = mutableStateOf(EmailValidateState())
    val emailValidateState = _emailValidate
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val schoolNames = listOf(
        SchoolName("광남고등학교"),
        SchoolName("휘문고등학교"),
        SchoolName("숙명여자고등학교"),
        SchoolName("아주고등학교"),
        SchoolName("유신고등학교"),
        SchoolName("창현고등학교"),
        SchoolName("팔달고등학교"),
        SchoolName("원천고등학교"),
        SchoolName("광양고등학교"),
        SchoolName("대원외국어고등학교")
        )

    private val _schools = MutableStateFlow(schoolNames)

    val schools = searchText
        //Flow에서 값을 변환하지 않고 각 값을 처리하기 위한 연산자
        .onEach { _isSearching.update { true } }
        .combine(_schools) { text, schools ->
            if(text.isBlank()) {
                schools
            } else {
                schools.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _schools.value
        )
    //stateIn : flow를 변경 가능한 stateFlow로 변환해주는 Flow.

    fun onSearchTextChange(text: String) {
        Log.e("onSearchText",text)
        _searchText.value = text
    }

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
    fun postSignUp(signUpState: MutableState<SignupInfo>){
        val signUpRequest : SignUpRequest = SignUpRequest(
            admissionYear = signUpState.value.admissionYear,
            classNum = if(signUpState.value.role =="TEACHER")null else signUpState.value.studentClass.toInt(),
            email = signUpState.value.email,
            role  = signUpState.value.role,
            grade =  if(signUpState.value.role =="TEACHER")null else signUpState.value.grade.toInt(),
            name = signUpState.value.name,
            number =  if(signUpState.value.role =="TEACHER")null else signUpState.value.studentId.toInt(),
            password = signUpState.value.password,
            school = signUpState.value.school
        )
        postSignUp(signUpRequest).onEach {
            result ->

            when(result)
            {
                is Resource.Success -> {
                    if(result.code == 201){
                        _signUpApiState.value  =SignUpApiState(
                            isSuccess = true,
                            isLoading = false
                        )
                    }
                }

                is Resource.Loading -> {
                    _signUpApiState.value  =SignUpApiState(
                        isLoading = true,
                        isSuccess = false
                    )
                }
                is Resource.Error -> {
                    _signUpApiState.value  =SignUpApiState(
                        isError = true
                    )
                }
        }


        }.launchIn(viewModelScope)
    }

    fun emailRequest(email:String, isToast :  () -> Unit ){
       emailRequestUseCase(email).onEach {
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
                        state= state.copy(emailError = "이미 등록된 이메일입니다.")
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
        emailValidateUseCase(email,authCode).onEach {
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
}


