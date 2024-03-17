package com.app.note_lass.module.dashboard.domain

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.dashboard.data.notice.Notice
import com.app.note_lass.module.home.tab.notice.DashBoard
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DashboardRepository {

    suspend fun createNotice(
        accessToken: String, groupId: Long,
        noticeContents: RequestBody,
        fileList: List<MultipartBody.Part?>
    ): NoteResponseBody<Nothing>

    suspend fun modifyNotice(
        accessToken: String,
        groupId: Long,
        noticeId: Long,
        noticeContents: RequestBody,
        fileList: List<MultipartBody.Part?>
    ): NoteResponseBody<Nothing>

    suspend fun getNoticeList(accessToken: String, groupId: Long) : NoteResponseBody<List<Notice>>
    suspend fun getNoticeDetail(accessToken: String, noticeId: Long) : NoteResponseBody<Notice>

    @GET("api/dashboard")
    suspend fun getDashboardsInHome(
        @Header(value = "Authorization") accessToken : String,
    ) : NoteResponseBody<List<DashBoard>>

    @GET("api/dashboard/{groupId}")
    suspend fun getDashboardsInGroup(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long,
    ) : NoteResponseBody<List<DashBoard>>
}