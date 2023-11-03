package com.app.note_lass.module.student.domain.usecase

import android.util.Log
import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginRequest
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.login.domain.repository.StudentRepository
import com.app.note_lass.module.student.data.HandBookRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostHandBookUseCase @Inject constructor(
    val studentRepository: StudentRepository,
    val dataStore: DataStore<Token>
) {

    operator fun invoke(groupId : Int, userId : Int,handBookRequest: HandBookRequest) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            emit(Resource.Loading())
            Log.e("handBookRequest",handBookRequest.content.toString())
            val token = "Bearer ${dataStore.data.first().accessToken}"
            val handBookResponse = studentRepository.postHandBook(accessToken = token, groupId = groupId,userId = userId,handBookRequest = handBookRequest)

            emit(
                Resource.Success(
                    data = handBookResponse,
                    code = handBookResponse.code,
                    message = handBookResponse.message
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
