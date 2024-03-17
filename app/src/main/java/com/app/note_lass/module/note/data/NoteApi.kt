package com.app.note_lass.module.note.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.dashboard.data.Material.Material
import com.app.note_lass.module.dashboard.data.notice.Materials
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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
        @Part("materialDto") materialContents: RequestBody,
        @Part fileList: MultipartBody.Part
        ) : NoteResponseBody<Nothing>
    @Multipart
    @PUT("api/material/{groupId}/{materialId}")
    suspend fun modifyMaterial(
        @Header(value = "Authorization") accessToken: String,
        @Path("groupId") groupId: Long,
        @Path("materialId") noticeId: Long,
        @Part("materialDto") noticeContents: RequestBody,
        @Part fileList: MultipartBody.Part?
    ) : NoteResponseBody<Nothing>

    @POST("api/material")
    suspend fun getMaterialToNote(
        @Header(value = "Authorization") accessToken : String,
        @Query(value = "fileId") id : Long
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

    @GET("api/file/{fileId}")
    suspend fun getFile(
        @Header(value = "Authorization") accessToken : String,
        @Path("fileId") fileId: Long
    ) : ResponseBody

    @GET("api/material/detail")
    suspend fun getMaterialDetail(
        @Header(value = "Authorization") accessToken : String,
        @Query(value = "materialId") materialId : Long
    ) : NoteResponseBody<Materials>

    @DELETE("api/note/{noteId}")
    suspend fun deleteNote(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "noteId") materialId : Long
    ) : NoteResponseBody<Nothing>

}