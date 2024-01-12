package com.app.note_lass.module.note.domain

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.note.data.Note
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface NoteRepository {
    suspend fun getNoteList(accessToken : String) : NoteResponseBody<List<Note>>
    suspend fun makeMaterial(accessToken: String, groupId: Long, noteRequest: RequestBody, fileList: MultipartBody.Part) : NoteResponseBody<Nothing>

}