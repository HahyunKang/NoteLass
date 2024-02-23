package com.app.note_lass.module.record.domain

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.record.data.EvaluationAnswer
import com.app.note_lass.module.record.data.EvaluationQuestion
import com.app.note_lass.module.record.data.Evaluations
import com.app.note_lass.module.record.data.File
import com.app.note_lass.module.record.data.Question
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
    suspend fun getSynonym(token: String, word :String) : NoteResponseBody<List<String>>
    suspend fun getGuideline(
        token: String, groudId: Long, userId: Long, keywords: String, handbookIds: String
    ) : NoteResponseBody<String>
    suspend fun getQuestions(
        token: String,groupId: Long
    ) : NoteResponseBody<List<EvaluationQuestion>>

    suspend fun postQuestions(
        token: String,groupId: Long,questions : List<Question>
    ) : NoteResponseBody<Nothing>

    suspend fun modifyQuestions(
        token: String,groupId: Long,questions : List<EvaluationQuestion>
    ) : NoteResponseBody<Nothing>

    suspend fun getAnswers(
        token: String,groupId: Long
    ) : NoteResponseBody<List<Evaluations>>

    suspend fun postAnswers(
        token: String,groupId: Long,questions : List<EvaluationAnswer>
    ) : NoteResponseBody<Nothing>

    suspend fun modifyAnswers(
        token: String,groupId: Long,questions : List<EvaluationAnswer>
    ) : NoteResponseBody<Nothing>
}