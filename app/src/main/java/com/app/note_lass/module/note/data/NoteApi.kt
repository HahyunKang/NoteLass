package com.app.note_lass.module.note.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.upload.data.Material.Material
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.annotation.processing.Generated

interface NoteApi {
    @GET("api/note")
    suspend fun getNote(
        @Header(value = "Authorization") accessToken : String,
    ) : NoteResponseBody<List<Note>>
    @Multipart
    @POST("api/material/{groupId}")
    suspend fun makeMaterial(
        @Header(value = "Authorization") accessToken : String,
        @Path("groupId") groupId: Long,
        @Part("materialCreateDto") materialContents: RequestBody,
        @Part fileList: MultipartBody.Part
        ) : NoteResponseBody<Nothing>

    @POST("api/material")
    suspend fun getMaterialToNote(
        @Header(value = "Authorization") accessToken : String,
        @Query(value = "materialId") id : Long
    ) : NoteResponseBody<Nothing>

    @GET("api/material/latest")
    suspend fun getLatestUploadMaterial(
        @Header(value = "Authorization") accessToken : String,
    ) : NoteResponseBody<List<Material>>

    @GET("api/note/{noteId}")
    suspend fun accessNote(
        @Header(value = "Authorization") accessToken : String,
        @Path("noteId") noteId: Long
    ) : NoteResponseBody<NoteAccessedDto>

    @GET("api/note/latest")
    suspend fun getLatestNote(
        @Header(value = "Authorization") accessToken : String,
    ) : NoteResponseBody<List<Note>>



}