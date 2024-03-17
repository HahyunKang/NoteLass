package com.app.note_lass.module.login.domain.repository

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.login.data.Auth
import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginDtoTemp
import com.app.note_lass.module.login.data.LoginRequest

interface LoginRepository {
    suspend fun login(loginRequest: LoginRequest) : NoteResponseBody<Auth>
    suspend fun logout(accessToken : String,refreshToken: String) : NoteResponseBody<Nothing>

    suspend fun refresh(accessToken: String,refreshToken: String): NoteResponseBody<Auth>
}