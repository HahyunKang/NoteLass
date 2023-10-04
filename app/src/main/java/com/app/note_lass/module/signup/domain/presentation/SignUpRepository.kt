package com.app.note_lass.module.signup.domain.presentation

import com.app.note_lass.module.signup.data.SignUpDto
import com.app.note_lass.module.signup.data.SignUpRequest
import javax.inject.Inject


interface  SignUpRepository{

    suspend fun postSignUp(signUpRequest: SignUpRequest) : SignUpDto

}