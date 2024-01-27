package com.app.note_lass.module.signup.domain.usecase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.module.signup.data.SignUpRequest
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class SignUpUseCase @Inject constructor(
    val repository: SignUpRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(signUpRequest: SignUpRequest) : Flow<Resource<NoteResponseBody<Nothing>>> = flow {

        try {
            emit(Resource.Loading())
            val signUpResponse = repository.postSignUp(signUpRequest)
            emit(Resource.Success(
                data = signUpResponse,
                code = signUpResponse.code,
                message = signUpResponse.message
            )
            )
        }
        catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }


    }

}