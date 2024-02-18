package com.app.note_lass.module.group.domain.repository

import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ApproveAllGroupUseCase @Inject constructor(
    val groupRepository: GroupRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(groupId : Long) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"

            emit(Resource.Loading())

            val groupResponse = groupRepository.approveAllGroup(token,groupId)

            emit(
                Resource.Success(
                    data = groupResponse,
                    code = groupResponse.code,
                    message = groupResponse.message
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
