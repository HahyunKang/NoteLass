package com.app.note_lass.module.login.data

import com.app.note_lass.common.NoteResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : NoteResponseBody<LoginDto>

    @POST("api/auth/logout")
    suspend fun logout(
        @Header(value = "Authorization") accessToken : String
    ) : NoteResponseBody<Nothing>
}