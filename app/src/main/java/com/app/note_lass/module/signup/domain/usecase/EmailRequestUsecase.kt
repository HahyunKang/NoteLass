package com.app.note_lass.module.signup.domain.usecase

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.module.signup.data.SignUpRequest
import com.app.note_lass.module.signup.domain.presentation.SignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class EmailRequestUseCase @Inject constructor(
    val repository: SignUpRepository
) {

    operator fun invoke(email:String) : Flow<Resource<NoteResponseBody<Nothing>>> = flow {

        try {
            emit(Resource.Loading())
            val emailResponse = repository.emailRequest(email)
            emit(Resource.Success(
                data = emailResponse,
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