package com.app.note_lass.module.student.domain.usecase.record

import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostExcelUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
    val dataStore : DataStore<Token>,
    private val dataGroupStore : DataStore<GroupInfo>
) {

    operator fun invoke(excelFile : MultipartBody.Part) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"
            val groupId= dataGroupStore.data.first().groupId

            emit(Resource.Loading())

            val recordResponse = studentRepository.postExcel(token,groupId!!,excelFile)

            emit(
                Resource.Success(
                    data = recordResponse,
                    code = recordResponse.code,
                    message = recordResponse.message
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
