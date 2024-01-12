package com.app.note_lass.module.upload.domain

import androidx.datastore.core.DataStore
import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.note.domain.NoteRepository
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

class PostMaterialUseCase @Inject constructor(
    val noteRepository: NoteRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(groupId : Long, noteRequest: NoteRequest, fileList: MultipartBody.Part) : Flow<Resource<NoteResponseBody<Nothing>>> = flow{
        try {
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"
            val jsonObject = JSONObject("{\"title\":\"${noteRequest.title}\",\"content\":\"${noteRequest.content}\"}").toString()

            val jsonBody = RequestBody.create("application/json".toMediaTypeOrNull(),jsonObject)
            val noteResponse = noteRepository.makeMaterial(token,groupId, jsonBody, fileList)

            emit(
                Resource.Success(
                    data = noteResponse,
                    code = noteResponse.code,
                    message = noteResponse.message
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
