package com.app.note_lass.module.signup.data


import com.app.note_lass.common.NoteResponseBody
import retrofit2.http.Body
import retrofit2.http.POST


interface SignupApi {
    @POST("api/auth/signup")
    suspend fun postSignUp(
        @Body signUpRequest : SignUpRequest,
    ): NoteResponseBody<Nothing>
}