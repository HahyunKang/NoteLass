package com.app.note_lass.module.note.domain

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.note.data.Note
import com.app.note_lass.module.note.data.NoteAccessedDto
import com.app.note_lass.module.upload.data.Material.Material
import com.app.note_lass.module.upload.data.notice.Materials
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody

interface NoteRepository {
    suspend fun getNoteList(accessToken : String) : NoteResponseBody<List<Note>>
    suspend fun makeMaterial(accessToken: String, groupId: Long, noteRequest: RequestBody, fileList: MultipartBody.Part) : NoteResponseBody<Nothing>
    suspend fun getMaterialToNote(accessToken: String, materialId : Long) : NoteResponseBody<Nothing>
    suspend fun getLatestUploadMaterial(accessToken: String) : NoteResponseBody<List<Material>>
    suspend fun accessNote(accessToken: String,noteId : Long) : NoteResponseBody<NoteAccessedDto>
    suspend fun getLatestNote(accessToken: String) : NoteResponseBody<List<Note>>
    suspend fun getFile(accessToken: String,fileId : Long) : ResponseBody
    suspend fun getMaterialDetail(accessToken: String,materialId: Long) : NoteResponseBody<Materials>
    suspend fun modifyMaterial(accessToken: String, groupId: Long, materialId: Long, noteRequest: RequestBody, fileList: MultipartBody.Part?) : NoteResponseBody<Nothing>


}