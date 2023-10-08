package com.app.note_lass.module.login.domain

import android.util.Log
import com.app.note_lass.common.Resource
import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginDtoTemp
import com.app.note_lass.module.login.data.LoginRequest
import com.app.note_lass.module.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    val loginRepository: LoginRepository
) {

    operator fun invoke(loginRequest: LoginRequest) : Flow<Resource<LoginDtoTemp>> = flow{
        try {
            emit(Resource.Loading())
            val loginResponse = loginRepository.login(loginRequest)
            Log.e("response",loginResponse.jwtToken)
            emit(
                Resource.Success(
                data = loginResponse,
                    code = null,
                    message = null
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
