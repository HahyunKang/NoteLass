package com.app.note_lass.module.upload.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.upload.data.assignment.Assignment
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UploadApi {
    @POST("api/assignment/{groupId}")
    suspend fun createAssignment(
     @Header("authorization") accessToken : String,
     @Path("groupId") groupId : Long,
     @Body assignmentRequest : Assignment
    ) : NoteResponseBody<Nothing>

    @GET("api/assignment/{groupId}")
    suspend fun getAssignmentList(
        @Header("authorization") accessToken: String,
        @Path("groupId") groupId : Long
    ) :NoteResponseBody<Nothing>

}