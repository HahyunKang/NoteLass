package com.app.note_lass.module.student.domain.usecase

import androidx.datastore.core.DataStore
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.login.domain.repository.StudentRepository
import com.app.note_lass.module.dashboard.data.Material.Material
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMaterialListUseCase @Inject constructor(
    private val studentRepository: StudentRepository,
    val dataStore: DataStore<Token>,
    val dataGroupStore: DataStore<GroupInfo>
) {

    operator fun invoke(groupId : Long): Flow<Resource<List<Material>>> = flow{
        try {
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"
            val response = studentRepository.getMaterialList(accessToken = token, groupId = groupId.toInt())

            emit(
                Resource.Success(
                    data =response.result!!,
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
