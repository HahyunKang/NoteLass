package com.app.note_lass.module.signup.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class SignUpImpl @Inject constructor(
    private val api: SignupApi
) : SignUpRepository{
    override suspend fun postSignUp(signUpRequest: SignUpRequest): NoteResponseBody<Nothing> {
        return api.postSignUp(signUpRequest)
    }

    override suspend fun emailRequest(email: String): NoteResponseBody<Nothing> {
        return api.emailRequest(email)
    }

    override suspend fun emailValidate(email: String, authCode: String): NoteResponseBody<Boolean> {
        return api.emailValidate(email, authCode)
    }

}