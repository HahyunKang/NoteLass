package com.app.note_lass.module.group.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.groupList.Group
import com.app.note_lass.module.group.data.join.JoinDto
import com.app.note_lass.module.group.data.join.JoinStudentListDto
import com.app.note_lass.module.group.data.studentList.Student
import com.app.note_lass.module.upload.data.notice.Notice
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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


    @POST("api/group/approve/{groupId}")
    suspend fun approveAllGroup(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long,
    ) : NoteResponseBody<Nothing>

    @DELETE("api/group/reject/{groupId}/{userId}")
    suspend fun rejectGroup(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId: Long,
        @Path(value = "userId") userId: Long,
    ) : NoteResponseBody<Nothing>


    @Multipart
    @POST("api/notice/{groupId}")
    suspend fun createNotice(
        @Header(value = "Authorization") accessToken: String,
        @Path("groupId") groupId: Long,
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

}