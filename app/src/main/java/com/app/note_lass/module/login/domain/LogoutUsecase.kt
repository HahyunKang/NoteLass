package com.app.note_lass.module.login.domain

import android.util.Log
import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginDtoTemp
import com.app.note_lass.module.login.data.LoginRequest
import com.app.note_lass.module.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    val loginRepository: LoginRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke() : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"

            val logoutResponse = loginRepository.logout(accessToken = token)

            emit(
                Resource.Success(
                data = logoutResponse,
                    code = logoutResponse.code,
                    message = logoutResponse.message
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
