package com.app.note_lass.module.student.domain.usecase.handbook

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

class ModifyHandBookUseCase @Inject constructor(
    val studentRepository: StudentRepository,
    val dataStore: DataStore<Token>
) {

    operator fun invoke(handbookContentId: Long,content : String) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"
            val response =studentRepository.modifyHandBook(accessToken = token, handbookContentId = handbookContentId,content = content)

            emit(
                Resource.Success(
                    data = response.result ,
                    code = response.code,
                    message = response.message
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
