package com.app.note_lass.module.group.domain.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.group.data.studentList.Student
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStudentListUseCase @Inject constructor(
    val groupRepository: GroupRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(id : Int) : Flow<Resource<List<Student>>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"
            Log.e("token in GroupList",token)

            emit(Resource.Loading())

            val groupResponse = groupRepository.getStudentList(token,id)

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
