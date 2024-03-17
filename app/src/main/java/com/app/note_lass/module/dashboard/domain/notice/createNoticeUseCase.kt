package com.app.note_lass.module.dashboard.domain.notice

import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.dashboard.data.notice.NoticeContents
import com.app.note_lass.module.dashboard.domain.DashboardRepository
import com.app.note_lass.module.group.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateNoticeUseCase @Inject constructor(
    val dashboardRepository: DashboardRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(
        groupId: Long,
        title:String,
        content:String,
        fileList: List<MultipartBody.Part?>
    ) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"

            emit(Resource.Loading())
            val notice = NoticeContents(title, content)
            val jsonObject = JSONObject("{\"title\":\"${title}\",\"content\":\"${content}\"}").toString()
            val jsonBody = RequestBody.create("application/json".toMediaTypeOrNull(),jsonObject)
            val groupResponse = dashboardRepository.createNotice(token,groupId,jsonBody,fileList)

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
