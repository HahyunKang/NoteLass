package com.app.note_lass.module.group.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.applicationList.ApplicationStudent
import com.app.note_lass.module.group.data.groupList.Group
import com.app.note_lass.module.group.data.groupList.GroupListDto
import com.app.note_lass.module.group.data.studentList.Student
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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
    suspend fun getStudentApplicationList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Int
    ) : NoteResponseBody<List<ApplicationStudent>>

    @GET("api/group/{code}")
    suspend fun enterGroup(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "code") code : Int
    ) : NoteResponseBody<String>


}