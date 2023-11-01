package com.app.note_lass.module.login.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.data.LoginRequest
import com.app.note_lass.module.login.data.LoginState
import com.app.note_lass.module.login.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    ) :ViewModel(){

    private var _loginState = mutableStateOf(LoginState())
    val loginState = _loginState

    fun login(email: String, password: String, tokenViewModel: ProtoViewModel){
        val loginRequest=  LoginRequest(email = email, password = password)
        loginUseCase(loginRequest).onEach {
            result ->
                    when(result){
                        is Resource.Success -> {
                            _loginState.value = LoginState(
                                isSuccess = true,
                                isMessage = result.data?.token!!
                            )
                            Log.e("role in Login",result.data.role)
                            Log.e("token in Login",result.data.token)

                            tokenViewModel.updateAccessToken(result.data.token)
                            if(result.data.role == "STUDENT") tokenViewModel.updateRole(Role.STUDENT)
                            else tokenViewModel.updateRole(Role.TEACHER)

                        }
                        is Resource.Error -> {
                            Log.e("login api",result.message.toString())
                            _loginState.value = LoginState(
                                isError= true
                            )
                        }

                        is Resource.Loading->{
                            _loginState.value = LoginState(
                                isSuccess = false,
                                isLoading = true
                            )
                        }
                    }



        }.launchIn(viewModelScope)


    }

}