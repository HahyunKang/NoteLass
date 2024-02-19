package com.app.note_lass.module.signup.domain.usecase

import com.app.note_lass.common.Resource
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class EmailValidateUseCase @Inject constructor(
    val repository: SignUpRepository
) {

    operator fun invoke(email:String,authCode:String) : Flow<Resource<Boolean>> = flow {

        try {
            emit(Resource.Loading())
            val emailResponse = repository.emailValidate(email, authCode)
            emit(Resource.Success(
                data = emailResponse.result!!,
                code = emailResponse.code,
                message = emailResponse.message
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