package com.app.note_lass.module.student.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resources
import com.app.note_lass.module.login.domain.repository.StudentRepository
import com.app.note_lass.module.student.data.Evaluation.EvaluationAnswer
import com.app.note_lass.module.student.data.Evaluation.EvaluationQuestion
import com.app.note_lass.module.student.data.Evaluation.Evaluations
import com.app.note_lass.module.student.data.Evaluation.Question
import com.app.note_lass.module.student.data.record.RecordBody
import com.app.note_lass.module.student.data.record.RecordScore
import com.app.note_lass.module.student.data.handbook.HandBook
import com.app.note_lass.module.student.data.handbook.HandBookRequest
import com.app.note_lass.module.dashboard.data.Material.Material
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class StudentImpl @Inject constructor(
    private val studentApi: StudentApi
): StudentRepository {
    override suspend fun postHandBook(accessToken: String, groupId:Int,userId : Int,handBookRequest: HandBookRequest): NoteResponseBody<Nothing> {
        return studentApi.postHandBook(accessToken,groupId,userId,handBookRequest)
    }

    override suspend fun getHanBookList(
        accessToken: String,
        groupId: Int,
        userId: Int
    ): NoteResponseBody<List<HandBook>> {
        return studentApi.getHandBookList(accessToken, groupId, userId)
    }

    override suspend fun getNoticeList(
        accessToken: String,
        groupId: Int
    ): NoteResponseBody<List<Resources>> {
        return studentApi.getNoticeList(accessToken, groupId)
    }

    override suspend fun getMaterialList(
        accessToken: String,
        groupId: Int
    ): NoteResponseBody<List<Material>> {
        return studentApi.getMaterialList(accessToken, groupId)
    }

    override suspend fun deleteHandBook(
        accessToken: String,
        handbookContentId: Long
    ) : Response<Unit> {
        return studentApi.deleteHandBook(accessToken, handbookContentId)
    }

    override suspend fun modifyHandBook(
        accessToken: String,
        handbookContentId: Long,
        content: String
    ): NoteResponseBody<Nothing> {
        return studentApi.modifyHandBook(accessToken, handbookContentId, content)
    }

    override suspend fun getRecord(token : String, groupId: Long, userId: Long) : NoteResponseBody<String> {
        return studentApi.getRecord(token,groupId,userId)
    }

    override suspend fun postRecord(token : String, groupId: Long, userId: Long,recordBody: RecordBody): NoteResponseBody<Nothing> {
        return studentApi.postRecord(token,groupId,userId,recordBody)
    }

    override suspend fun postExcel(
        token: String,
        groupId: Long,
        excelFile: MultipartBody.Part
    ): NoteResponseBody<Nothing> {
        return studentApi.postExcel(token,groupId, excelFile)
    }

    override suspend fun getExcel(token: String, groupId: Long): NoteResponseBody<File> {
        return studentApi.getExcel(token,groupId)
    }

    override suspend fun deleteExcel(token: String, groupId: Long): NoteResponseBody<Nothing> {
        return studentApi.deleteExcel(token,groupId)
    }

    override suspend fun getRecordScore(
        token: String,
        groupId: Long,
        userId: Long,
        percentage: Int
    ): NoteResponseBody<RecordScore> {
        return studentApi.getScore(token,groupId, userId, percentage)
    }

    override suspend fun getSynonym(token: String, word: String): NoteResponseBody<List<String>> {
        return studentApi.getSynonym(token,word)
    }

    override suspend fun getGuideline(
        token: String,
        groudId: Long,
        userId: Long,
        keywords: String,
        handbookIds: String
    ): NoteResponseBody<String> {
        return studentApi.getGuideline(token,groudId,userId, keywords, handbookIds)
    }

    override suspend fun getQuestions(
        token: String,
        groupId: Long
    ): NoteResponseBody<List<EvaluationQuestion>> {
        return studentApi.getQuestions(token,groupId)
    }

    override suspend fun postQuestions(
        token: String,
        groupId: Long,
        questions: List<Question>
    ): NoteResponseBody<Nothing> {
        return studentApi.postQuestions(token,groupId,questions)
    }

    override suspend fun modifyQuestions(
        token: String,
        groupId: Long,
        questions: List<EvaluationQuestion>
    ): NoteResponseBody<Nothing> {
        return studentApi.modifyQuestions(token,groupId,questions)
    }

    override suspend fun getAnswers(
        token: String,
        groupId: Long
    ): NoteResponseBody<List<Evaluations>> {
        return studentApi.getAnswers(token,groupId)
    }

    override suspend fun postAnswers(
        token: String,
        groupId: Long,
        questions: List<EvaluationAnswer>
    ): NoteResponseBody<Nothing> {
        return studentApi.postAnswers(token,groupId,questions)
    }

    override suspend fun modifyAnswers(
        token: String,
        groupId: Long,
        questions: List<EvaluationAnswer>
    ): NoteResponseBody<Nothing> {
        return studentApi.modifyAnswers(token,groupId,questions)
    }

    override suspend fun getStudentEvaluations(
        token: String,
        groupId: Long,
        userId: Long
    ): NoteResponseBody<List<Evaluations>> {
        return studentApi.getStudentEvaluations(token,groupId, userId)
    }

    override suspend fun getIntroduction(
        token: String,
        groupId: Long,
        userId: Long
    ): NoteResponseBody<String> {
        return studentApi.getIntroduction(token,groupId, userId)
    }

}