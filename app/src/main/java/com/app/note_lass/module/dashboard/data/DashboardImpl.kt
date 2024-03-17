package com.app.note_lass.module.dashboard.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.dashboard.data.notice.Notice
import com.app.note_lass.module.dashboard.domain.DashboardRepository
import com.app.note_lass.module.group.data.GroupApi
import com.app.note_lass.module.group.domain.repository.GroupRepository
import com.app.note_lass.module.home.tab.notice.DashBoard
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class DashboardImpl @Inject constructor(
    private val dashboardApi: DashboardApi
): DashboardRepository {
    override suspend fun createNotice(
        accessToken: String,
        groupId: Long,
        noticeContents: RequestBody,
        fileList: List<MultipartBody.Part?>
    ): NoteResponseBody<Nothing> {
        return dashboardApi.createNotice(accessToken,groupId,noticeContents,fileList)
    }

    override suspend fun modifyNotice(
        accessToken: String,
        groupId: Long,
        noticeId: Long,
        noticeContents: RequestBody,
        fileList: List<MultipartBody.Part?>
    ): NoteResponseBody<Nothing> {
        return dashboardApi.modifyNotice(accessToken, groupId, noticeId, noticeContents, fileList)
    }

    override suspend fun getNoticeList(
        accessToken: String,
        groupId: Long
    ): NoteResponseBody<List<Notice>> {
        return dashboardApi.getNoticeList(accessToken, groupId)
    }

    override suspend fun getNoticeDetail(
        accessToken: String,
        noticeId: Long
    ): NoteResponseBody<Notice> {
        return dashboardApi.getNoticeDetail(accessToken, noticeId)
    }

    override suspend fun getDashboardsInHome(accessToken: String): NoteResponseBody<List<DashBoard>> {
        return dashboardApi.getDashboardsInHome(accessToken)
    }

    override suspend fun getDashboardsInGroup(
        accessToken: String,
        groupId: Long
    ): NoteResponseBody<List<DashBoard>> {
        return dashboardApi.getDashboardsInGroup(accessToken, groupId)
    }


}