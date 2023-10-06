package com.app.note_lass.module.signup.data

import androidx.compose.runtime.InternalComposeTracingApi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface SignupApi {
    @POST("api/auth/signup")
    suspend fun postSignUp(
        @Body signUpRequest : SignUpRequest,
    ): Response<Unit>
}