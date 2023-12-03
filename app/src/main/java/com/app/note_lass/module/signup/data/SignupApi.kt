package com.app.note_lass.module.signup.data


import com.app.note_lass.common.NoteResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface SignupApi {
    @POST("api/auth/signup")
    suspend fun postSignUp(
        @Body signUpRequest : SignUpRequest,
    ): NoteResponseBody<Nothing>
    @POST("api/auth/email/request")
    suspend fun emailRequest(
        @Query(value = "email") email : String
    ) : NoteResponseBody<Nothing>

    @GET("api/auth/email/verification")
    suspend fun emailValidate(
        @Query(value = "email") email : String,
        @Query(value = "authCode") authCode : String
    ) : NoteResponseBody<Boolean>
}