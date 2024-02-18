package com.app.note_lass.module.note.domain

import android.util.Log
import androidx.datastore.core.DataStore
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class GetFileUsecase @Inject constructor(
    val noteRepository: NoteRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(fileId :Long) : Flow<Resource<InputStream>> = flow{
        try {
            emit(Resource.Loading())
            val token = "Bearer ${dataStore.data.first().accessToken}"

            val noteResponse = noteRepository.getFile(accessToken = token, fileId = fileId)
            Log.e("noteResponse","noteResponse")

            val fileData = noteResponse.byteStream()
            Log.e("noteResponse","noteResponse")

            emit(
                Resource.Success(
                    data = fileData,
                    code = 200,
                    message = null
            )

            )
        }
        catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            e.localizedMessage?.let { Log.e("errorInFile", it) }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}
