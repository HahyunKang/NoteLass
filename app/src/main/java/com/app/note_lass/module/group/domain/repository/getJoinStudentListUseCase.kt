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
import com.app.note_lass.module.group.data.join.JoinDto
import com.app.note_lass.module.group.data.join.JoinStudentInfo
import com.app.note_lass.module.group.data.join.JoinStudentListDto
import com.app.note_lass.module.group.data.studentList.Student
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

class GetJoinStudentListUseCase @Inject constructor(
    val groupRepository: GroupRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(groupId : Long) : Flow<Resource<JoinStudentListDto>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"

            emit(Resource.Loading())

            val groupResponse = groupRepository.getStudentJoinList(token,groupId)

            emit(
                Resource.Success(
                    data = groupResponse.result!!,
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
