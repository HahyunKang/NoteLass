package com.app.note_lass.module.note.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.note.domain.NoteRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class NoteImpl @Inject constructor(
    private val noteApi : NoteApi
): NoteRepository {
    override suspend fun getNoteList(accessToken: String): NoteResponseBody<List<Note>> {
        return noteApi.getNote(accessToken)
    }

    override suspend fun makeMaterial(
        accessToken: String,
        groupId: Long,
        noteRequest: RequestBody,
        fileList: MultipartBody.Part
    ): NoteResponseBody<Nothing> {
        return noteApi.makeMaterial(accessToken, groupId, noteRequest,fileList)
    }

    override suspend fun getMaterialToNote(
        accessToken: String,
        materialId: Long
    ): NoteResponseBody<Nothing> {
        return noteApi.getMaterialToNote(accessToken,materialId)
    }


}