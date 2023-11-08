package com.app.note_lass.module.group.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.groupList.Group
import com.app.note_lass.module.group.data.join.JoinDto
import com.app.note_lass.module.group.data.join.JoinStudentListDto
import com.app.note_lass.module.group.data.studentList.Student
import com.app.note_lass.module.group.data.upload.notice.Notice
import com.app.note_lass.module.group.data.upload.notice.NoticeContents
import com.app.note_lass.module.group.data.upload.notice.NoticeListDto
import kotlinx.coroutines.internal.PrepareOp
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupApi {

    @POST("api/group")
    suspend fun createGroup(
        @Header(value = "Authorization") accessToken : String,
        @Body groupCreateRequest : InfoForCreate
    ) : NoteResponseBody<Int>

    @GET("api/group")
    suspend fun getGroupList(
        @Header(value = "Authorization") accessToken : String,
    ) : NoteResponseBody<List<Group>>
    @GET("api/group/students/{groupId}")
    suspend fun getStudentList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Int
    ) : NoteResponseBody<List<Student>>

    @GET("api/group/applications/{groupId}")
    suspend fun getStudentJoinList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long
    ) : NoteResponseBody<JoinStudentListDto>

    @GET("api/group/{code}")
    suspend fun enterGroup(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "code") code : Int
    ) : NoteResponseBody<JoinDto>

    @GET("api/group/join/{groupId}")
    suspend fun joinGroup(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long
    ) : NoteResponseBody<Nothing>
    @POST("api/group/approve/{groupId}/{userId}")
    suspend fun approveGroup(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long,
        @Path(value = "userId") userId: Long,
    ) : NoteResponseBody<Nothing>
    @DELETE("api/group/reject/{groupId}/{userId}")
    suspend fun rejectGroup(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long,
        @Path(value = "userId") userId: Long,
    ) : NoteResponseBody<Nothing>


    @POST("api/notice/{groupId}")
    suspend fun createNotice(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long,
        @Body noticeContents: NoticeContents
    ) : NoteResponseBody<Nothing>

    @GET("api/notice/{groupId}")
    suspend fun getNoticeList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long,
    ) : NoteResponseBody<NoticeListDto>

    @GET("api/notice/detail")
    suspend fun getNoticeDetail(
        @Header(value = "Authorization") accessToken : String,
        @Query(value = "noticeId") noticeId : Long
    ) : NoteResponseBody<Notice>

}