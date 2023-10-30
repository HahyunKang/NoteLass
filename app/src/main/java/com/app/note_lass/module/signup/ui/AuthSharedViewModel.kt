package com.app.note_lass.module.signup.ui

import android.util.Log
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.core.navigation.AuthScreen
import com.app.note_lass.module.signup.data.SchoolName
import com.app.note_lass.module.signup.data.SignUpApiState
import com.app.note_lass.module.signup.data.SignUpRequest
import com.app.note_lass.module.signup.domain.usecase.ValidateEmail
import com.app.note_lass.module.signup.domain.usecase.ValidatePassWord
import com.app.note_lass.module.signup.domain.usecase.ValidateRepeatedPassWord
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormEvent
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormState
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
    private val postSignUp : SignUpUseCase
) :ViewModel() {
   //매번 새로운 객체 생성
    var state by mutableStateOf(RegistrationFormState())

   // var signupState by mutableStateOf(SignupInfo())
    var signupState =
        mutableStateOf(SignupInfo())

    private val _signUpApiState = mutableStateOf(SignUpApiState())
    val signUpApiState = _signUpApiState


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

            else -> {}
        }


    }


    fun postSignUp(signUpState: MutableState<SignupInfo>){
        Log.e("signup Api",signUpState.value.email)
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
                    Log.e("signup Api SUCCESS ", result.message.toString())
                    Log.e("signup Api Success", result.code.toString())
                    result.data?.let { Log.e("signup Api Success", it.message.toString()) }

                    if(result.code == 201){
                        _signUpApiState.value  =SignUpApiState(
                            isSuccess = true
                        )

                    }


                }

                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    Log.e("signup Api", result.message.toString())

                }
        }


        }.launchIn(viewModelScope)
    }
}


