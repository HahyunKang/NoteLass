package com.app.note_lass.module.signup.data

import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class SignUpImpl @Inject constructor(
    private val api: SignupApi
) : SignUpRepository{
    override suspend fun postSignUp(signUpRequest: SignUpRequest): Response<ResponseBody?> {
        return api.postSignUp(signUpRequest)
    }

}