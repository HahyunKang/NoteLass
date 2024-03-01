package com.app.note_lass.module.group.domain.repository

import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.upload.data.notice.NoticeContents
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

class ModifyNoticeUseCase @Inject constructor(
    val groupRepository: GroupRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(
        groupId: Long,
        noticeId : Long,
        title:String,
        content:String,
        ids : List<Long>,
        fileList: List<MultipartBody.Part?>
    ) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"


            emit(Resource.Loading())
            val jsonObject = JSONObject("{\"title\":\"${title}\",\"content\":\"${content}\",\"removeFileIds\":${ids}}").toString()
            val jsonBody = RequestBody.create("application/json".toMediaTypeOrNull(),jsonObject)
            val groupResponse = groupRepository.modifyNotice(token,groupId,noticeId,jsonBody,fileList)

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
