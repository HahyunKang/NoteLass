package com.app.note_lass.module.dashboard.domain.material

import androidx.datastore.core.DataStore
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.note.domain.NoteRepository
import com.app.note_lass.module.dashboard.data.notice.Materials
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMaterialDetailUseCase @Inject constructor(
    val noteRepository: NoteRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(materialId : Long) : Flow<Resource<Materials>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"

            emit(Resource.Loading())

            val groupResponse = noteRepository.getMaterialDetail(token,materialId)
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
