package com.app.note_lass.module.login.data

import com.app.note_lass.common.NoteResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {

    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : NoteResponseBody<Auth>

    @POST("api/auth/logout")
    suspend fun logout(
        @Header(value = "Authorization") accessToken : String,
        @Header(value = "Cookie") refreshToken : String
    ) : NoteResponseBody<Nothing>

    @GET("api/auth/refresh")
    suspend fun refresh(
        @Header(value = "Authorization") accessToken : String,
        @Header(value = "Cookie") refreshToken : String
    ) : NoteResponseBody<Auth>
}