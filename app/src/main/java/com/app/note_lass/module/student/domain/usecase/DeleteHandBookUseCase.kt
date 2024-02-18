package com.app.note_lass.module.student.domain.usecase

import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DeleteHandBookUseCase @Inject constructor(
    val studentRepository: StudentRepository,
    val dataStore: DataStore<Token>
) {

    operator fun invoke(handbookContentId: Long) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"
            studentRepository.deleteHandBook(accessToken = token, handbookContentId = handbookContentId)

            emit(
                Resource.Success(
                    data = null,
                    code = 204,
                    message = "Delete is successed"
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
