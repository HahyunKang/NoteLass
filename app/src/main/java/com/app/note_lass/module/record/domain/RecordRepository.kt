package com.app.note_lass.module.record.domain

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.record.data.File
import com.app.note_lass.module.record.data.RecordBody
import com.app.note_lass.module.record.data.RecordScore
import okhttp3.MultipartBody

interface RecordRepository {
    suspend fun getRecord(token : String,groupId : Long, userId : Long) : NoteResponseBody<String>
    suspend fun postRecord(token: String, groupId : Long, userId : Long,recordBody: RecordBody) : NoteResponseBody<Nothing>
    suspend fun postExcel(token : String,groupId : Long, excelFile : MultipartBody.Part) : NoteResponseBody<Nothing>
    suspend fun getExcel(token : String,groupId : Long) : NoteResponseBody<File>
    suspend fun deleteExcel(token: String,groupId: Long) : NoteResponseBody<Nothing>
    suspend fun getRecordScore(token: String, groupId : Long, userId : Long, percentage : Int) : NoteResponseBody<RecordScore>

}