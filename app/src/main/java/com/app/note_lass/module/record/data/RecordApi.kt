package com.app.note_lass.module.record.data

import com.app.note_lass.common.NoteResponseBody
import kotlinx.serialization.Polymorphic
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RecordApi {

    @POST("api/record/excel/{groupId}/{userId}")
    suspend fun postRecord(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long,
        @Path(value = "userId")userId : Long,
        @Body recordBody : RecordBody
    ): NoteResponseBody<Nothing>


    @GET("api/record/excel/{groupId}/{userId}")
    suspend fun getRecord(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long,
        @Path(value = "userId")userId : Long,
    ): NoteResponseBody<String>
}