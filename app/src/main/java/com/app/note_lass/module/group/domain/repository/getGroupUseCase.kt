package com.app.note_lass.module.group.domain.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.group.data.GroupCreateDto
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupList.Group
import com.app.note_lass.module.group.data.groupList.GroupListDto
import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginDtoTemp
import com.app.note_lass.module.login.data.LoginRequest
import com.app.note_lass.module.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetGroupUseCase @Inject constructor(
    val groupRepository: GroupRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke() : Flow<Resource<List<Group>>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"
            Log.e("token in GroupList",token)

            emit(Resource.Loading())

            val groupResponse = groupRepository.getGroupList(token)
            Log.e("response in GroupList",groupResponse.result!!.size.toString())

            emit(
                Resource.Success(
                    data = groupResponse.result,
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
