package com.app.note_lass.module.student.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resources
import com.app.note_lass.module.group.data.applicationList.ApplicationStudent
import com.app.note_lass.module.group.data.groupList.Group
import com.app.note_lass.module.group.data.groupList.GroupListDto
import com.app.note_lass.module.group.data.studentList.Student
import com.app.note_lass.module.upload.data.Material.Material
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface StudentApi {

  @POST("api/handbook/{groupId}/{userId}")
  suspend fun postHandBook(
      @Header(value = "Authorization") accessToken : String,
      @Path(value = "groupId") groupId : Int,
      @Path(value = "userId") userId : Int,
      @Body handBookRequest: HandBookRequest
      ) : NoteResponseBody<Nothing>

  @GET("api/handbook/{groupId}/{userId}")
  suspend fun getHandBookList(
      @Header(value = "Authorization") accessToken : String,
      @Path(value = "groupId") groupId : Int,
      @Path(value = "userId") userId : Int,
  ) : NoteResponseBody<List<HandBook>>

    @GET("api/notice/{groupId}")
    suspend fun getNoticeList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Int,
    ) : NoteResponseBody<List<Resources>>


    @GET("api/material/{groupId}")
    suspend fun getMaterialList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Int,
    ): NoteResponseBody<List<Material>>
}