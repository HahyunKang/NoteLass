package com.app.note_lass.module.note.domain

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginDtoTemp
import com.app.note_lass.module.login.data.LoginRequest
import com.app.note_lass.module.note.data.Note
import okhttp3.MultipartBody

interface NoteRepository {
    suspend fun getNoteList(accessToken : String) : NoteResponseBody<List<Note>>
    suspend fun makeMaterial(accessToken : String,groupId : Long,fileList: MultipartBody.Part) : NoteResponseBody<Nothing>

}