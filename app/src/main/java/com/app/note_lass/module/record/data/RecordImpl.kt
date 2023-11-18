package com.app.note_lass.module.record.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.record.domain.RecordRepository
import okhttp3.MultipartBody
import java.io.File
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

    override suspend fun postExcel(
        token: String,
        groupId: Long,
        excelFile: MultipartBody.Part
    ): NoteResponseBody<Nothing> {
        return recordApi.postExcel(token,groupId, excelFile)
    }

    override suspend fun getExcel(token: String, groupId: Long): NoteResponseBody<File> {
        return recordApi.getExcel(token,groupId)
    }


}