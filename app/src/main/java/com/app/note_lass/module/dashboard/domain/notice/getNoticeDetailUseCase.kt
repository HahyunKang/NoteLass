package com.app.note_lass.module.dashboard.domain.notice

import androidx.datastore.core.DataStore
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.dashboard.data.notice.Notice
import com.app.note_lass.module.dashboard.domain.DashboardRepository
import com.app.note_lass.module.group.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNoticeDetailUseCase @Inject constructor(
    val dashboardRepository: DashboardRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(noticeId : Long) : Flow<Resource<Notice>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"

            emit(Resource.Loading())

            val groupResponse = dashboardRepository.getNoticeDetail(token,noticeId)
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
