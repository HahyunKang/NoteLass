package com.app.note_lass.module.dashboard.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.dashboard.data.assignment.Assignment
import com.app.note_lass.module.dashboard.data.notice.Notice
import com.app.note_lass.module.home.tab.notice.DashBoard
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface DashboardApi {

    @Multipart
    @POST("api/notice/{groupId}")
    suspend fun createNotice(
        @Header(value = "Authorization") accessToken: String,
        @Path("groupId") groupId: Long,
        @Part("noticeDto") noticeContents: RequestBody,
        @Part fileList: List<MultipartBody.Part?>
    ) : NoteResponseBody<Nothing>

    @Multipart
    @PUT("api/notice/{groupId}/{noticeId}")
    suspend fun modifyNotice(
        @Header(value = "Authorization") accessToken: String,
        @Path("groupId") groupId: Long,
        @Path("noticeId") noticeId: Long,
        @Part("noticeDto") noticeContents: RequestBody,
        @Part fileList: List<MultipartBody.Part?>
    ) : NoteResponseBody<Nothing>

    @GET("api/notice/{groupId}")
    suspend fun getNoticeList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long,
    ) : NoteResponseBody<List<Notice>>

    @GET("api/notice/detail")
    suspend fun getNoticeDetail(
        @Header(value = "Authorization") accessToken : String,
        @Query(value = "noticeId") noticeId : Long
    ) : NoteResponseBody<Notice>


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