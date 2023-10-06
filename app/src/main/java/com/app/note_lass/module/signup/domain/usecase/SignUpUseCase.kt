package com.app.note_lass.module.signup.domain.usecase

import com.app.note_lass.common.Resource
import com.app.note_lass.module.signup.data.SignUpRequest
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class SignUpUseCase @Inject constructor(
    val repository: SignUpRepository
) {

    operator fun invoke(signUpRequest: SignUpRequest) : Flow<Resource<ResponseBody?>> = flow {

        try {
            emit(Resource.Loading())
            val signUpResponse = repository.postSignUp(signUpRequest)
            emit(Resource.Success(
                data = signUpResponse.body(),
                code = signUpResponse.code(),
                message = signUpResponse.message()
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