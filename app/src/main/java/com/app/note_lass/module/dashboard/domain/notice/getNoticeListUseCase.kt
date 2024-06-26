package com.app.note_lass.module.dashboard.domain.notice

import androidx.datastore.core.DataStore
import com.app.note_lass.common.Resource
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.dashboard.data.notice.NoticePreview
import com.app.note_lass.module.dashboard.data.notice.toPreview
import com.app.note_lass.module.dashboard.domain.DashboardRepository
import com.app.note_lass.module.group.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    val dashboardRepository: DashboardRepository,
    val dataStore : DataStore<Token>
) {

    operator fun invoke(groupId : Long) : Flow<Resource<List<NoticePreview>>> = flow{
        try {
            val token = "Bearer ${dataStore.data.first().accessToken}"

            emit(Resource.Loading())

            val groupResponse = dashboardRepository.getNoticeList(token,groupId)
            val noticeList = groupResponse.result!!
            val noticePreviewList = noticeList.map { notice ->
                notice.toPreview()
            }
            emit(
                Resource.Success(
                    data = noticePreviewList,
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
