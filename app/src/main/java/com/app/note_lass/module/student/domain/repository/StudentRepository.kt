package com.app.note_lass.module.login.domain.repository

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resources
import com.app.note_lass.module.student.data.Evaluation.EvaluationAnswer
import com.app.note_lass.module.student.data.Evaluation.EvaluationQuestion
import com.app.note_lass.module.student.data.Evaluation.Evaluations
import com.app.note_lass.module.student.data.File
import com.app.note_lass.module.student.data.Evaluation.Question
import com.app.note_lass.module.student.data.record.RecordBody
import com.app.note_lass.module.student.data.record.RecordScore
import com.app.note_lass.module.student.data.handbook.HandBook
import com.app.note_lass.module.student.data.handbook.HandBookRequest
import com.app.note_lass.module.dashboard.data.Material.Material
import okhttp3.MultipartBody
import retrofit2.Response

interface StudentRepository {
    suspend fun postHandBook(
        accessToken : String,
        groupId : Int,
        userId :Int,
        handBookRequest: HandBookRequest
    ) : NoteResponseBody<Nothing>

    suspend fun getHanBookList(
        accessToken : String,
        groupId : Int,
        userId :Int
    ) : NoteResponseBody<List<HandBook>>
    suspend fun getNoticeList(
        accessToken : String,
        groupId : Int,
    ) : NoteResponseBody<List<Resources>>
    suspend fun getMaterialList(
        accessToken : String,
        groupId : Int,
    ) : NoteResponseBody<List<Material>>

    suspend fun deleteHandBook(
        accessToken: String,
        handbookContentId: Long
    ) : Response<Unit>
    suspend fun modifyHandBook(
        accessToken: String,
        handbookContentId: Long,
        content : String
    ) : NoteResponseBody<Nothing>

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

    suspend fun getStudentEvaluations(
        token: String,groupId: Long,userId: Long
    ) : NoteResponseBody<List<Evaluations>>

    suspend fun getIntroduction(
        token: String,groupId: Long,userId: Long
    ) : NoteResponseBody<String>
}