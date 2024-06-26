package com.app.note_lass.module.student.domain.usecase

import androidx.datastore.core.DataStore
import com.app.note_lass.common.Resource
import com.app.note_lass.common.Resources
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
    val dataStore: DataStore<Token>,
    val dataGroupStore: DataStore<GroupInfo>
) {

    operator fun invoke(): Flow<Resource<List<Resources>>> = flow{
        try {
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"
            val groupId = dataGroupStore.data.first().groupId!!.toInt()
            val handBookResponse = studentRepository.getNoticeList(accessToken = token, groupId = groupId)

            emit(
                Resource.Success(
                    data = handBookResponse.result!!,
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
