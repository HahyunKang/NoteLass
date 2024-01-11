package com.app.note_lass.module.note.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.InfoForCreate
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface NoteApi {
    @GET("api/note")
    suspend fun getNote(
        @Header(value = "Authorization") accessToken : String,
    ) : NoteResponseBody<List<Note>>

    @POST("api/material/{groupId}")
    suspend fun makeMaterial(
        @Header(value = "Authorization") accessToken : String,
        @Path("groupId") groupId: Long,
        @Part fileList: MultipartBody.Part
        ) : NoteResponseBody<Nothing>
}