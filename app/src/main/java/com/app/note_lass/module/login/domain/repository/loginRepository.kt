package com.app.note_lass.module.login.domain.repository

import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginDtoTemp
import com.app.note_lass.module.login.data.LoginRequest

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest) : LoginDtoTemp

}