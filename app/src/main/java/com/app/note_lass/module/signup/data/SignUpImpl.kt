package com.app.note_lass.module.signup.data

import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import retrofit2.Response
import javax.inject.Inject

class SignUpImpl @Inject constructor(
    private val api: SignupApi
) : SignUpRepository{
    override suspend fun postSignUp(signUpRequest: SignUpRequest): Response<Unit> {
        return api.postSignUp(signUpRequest)
    }

}