package com.app.note_lass.module.login.data

import com.app.note_lass.module.login.domain.repository.LoginRepository
import javax.inject.Inject

class LoginImpl @Inject constructor(
    private val loginApi: LoginApi
): LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): LoginDtoTemp {
        return loginApi.login(loginRequest)
    }

}