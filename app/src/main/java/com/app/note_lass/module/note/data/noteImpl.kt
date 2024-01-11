package com.app.note_lass.module.note.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.note.domain.NoteRepository
import okhttp3.MultipartBody
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
        fileList: MultipartBody.Part
    ): NoteResponseBody<Nothing> {
        return noteApi.makeMaterial(accessToken, groupId, fileList)
    }


}