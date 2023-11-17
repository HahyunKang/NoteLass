package com.app.note_lass.module.record.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.record.domain.RecordRepository
import javax.inject.Inject

class RecordImpl @Inject constructor(
    private val recordApi: RecordApi
): RecordRepository {
    override suspend fun getRecord(token : String, groupId: Long, userId: Long) : NoteResponseBody<String> {
       return recordApi.getRecord(token,groupId,userId)
    }

    override suspend fun postRecord(token : String, groupId: Long, userId: Long,recordBody: RecordBody): NoteResponseBody<Nothing> {
        return recordApi.postRecord(token,groupId,userId,recordBody)
    }


}