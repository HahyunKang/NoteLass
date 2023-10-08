package com.app.note_lass.core.Proto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProtoViewModel @Inject constructor(
    private val protoRepository: ProtoRepository
) : ViewModel(){

    val token = protoRepository.tokens

    fun updateAccessToken(accessToken : String) {
        viewModelScope.launch {
            protoRepository.updateAccessToken(accessToken)
            Log.d("accessToken",accessToken)
        }
    }

    fun updateRole(role : Role) {
        viewModelScope.launch {
            protoRepository.updateRole(role)
            Log.d("role",role.toString())
        }
    }

    fun resetToken(){
        viewModelScope.launch {
            protoRepository.resetToken()

        }
    }
}