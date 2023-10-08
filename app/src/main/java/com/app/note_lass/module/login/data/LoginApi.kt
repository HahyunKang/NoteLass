package com.app.note_lass.module.login.data

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : LoginDtoTemp

}