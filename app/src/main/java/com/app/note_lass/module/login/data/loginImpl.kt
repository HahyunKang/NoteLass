package com.app.note_lass.module.login.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginImpl @Inject constructor(
    private val loginApi: LoginApi
): LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): NoteResponseBody<Auth> {
        return loginApi.login(loginRequest)
    }

    override suspend fun logout(accessToken : String,refreshToken: String) : NoteResponseBody<Nothing>{
        return loginApi.logout(accessToken,refreshToken)
    }

    override suspend fun refresh(
        accessToken: String,
        refreshToken: String
    ): NoteResponseBody<Auth> {
        return loginApi.refresh(accessToken, refreshToken)
    }

}