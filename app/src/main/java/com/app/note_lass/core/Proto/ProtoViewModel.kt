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

    val groupInfo = protoRepository.groupInfo

    fun updateToken(accessToken : String,refreshToken : String) {
        viewModelScope.launch {
            Log.e("토큰 업데이트",refreshToken)
            protoRepository.updateToken(accessToken, refreshToken)
        }
    }

    fun updateRefreshToken(refreshToken : String) {
        viewModelScope.launch {
            protoRepository.updateRefreshToken(refreshToken)
        }
    }

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

    fun updateGroupInfo(groupInfo: GroupInfo){
        viewModelScope.launch {
            protoRepository.updateGroupInfo(groupInfo)
            groupInfo.groupName?.let { Log.e("GroupInfo", it) }
        }
    }

    fun resetGroupInfo(){
        viewModelScope.launch {
            protoRepository.resetGroupInfo()

        }
    }
}