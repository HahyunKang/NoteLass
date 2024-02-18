package com.app.note_lass.module.login.domain

import android.util.Log
import com.app.note_lass.common.Resource
import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginRequest
import com.app.note_lass.module.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    val loginRepository: LoginRepository
) {

    operator fun invoke(loginRequest: LoginRequest) : Flow<Resource<LoginDto>> = flow{
        try {
            emit(Resource.Loading())
            Log.e("loginRequest",loginRequest.email)
            Log.e("loginRequest",loginRequest.password)

            val loginResponse = loginRepository.login(loginRequest)

            emit(
                Resource.Success(
                data = loginResponse.result!!,
                    code = loginResponse.code,
                    message = loginResponse.message
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
