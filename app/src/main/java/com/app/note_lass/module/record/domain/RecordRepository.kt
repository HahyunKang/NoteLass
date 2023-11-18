package com.app.note_lass.module.record.domain

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.record.data.RecordBody

interface RecordRepository {
    suspend fun getRecord(token : String,groupId : Long, userId : Long) : NoteResponseBody<String>
    suspend fun postRecord(token: String, groupId : Long, userId : Long,recordBody: RecordBody) : NoteResponseBody<Nothing>

}