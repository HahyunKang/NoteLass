package com.app.note_lass.module.record.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.record.domain.RecordRepository
import okhttp3.MultipartBody
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

    override suspend fun getExcel(token: String, groupId: Long): NoteResponseBody<com.app.note_lass.module.record.data.File> {
        return recordApi.getExcel(token,groupId)
    }

    override suspend fun deleteExcel(token: String, groupId: Long): NoteResponseBody<Nothing> {
        return recordApi.deleteExcel(token,groupId)
    }

    override suspend fun getRecordScore(
        token: String,
        groupId: Long,
        userId: Long,
        percentage: Int
    ): NoteResponseBody<RecordScore> {
        return recordApi.getScore(token,groupId, userId, percentage)
    }

    override suspend fun getSynonym(token: String, word: String): NoteResponseBody<List<String>> {
        return recordApi.getSynonym(token,word)
    }

    override suspend fun getGuideline(
        token: String,
        groudId: Long,
        userId: Long,
        keywords: String,
        handbookIds: String
    ): NoteResponseBody<String> {
        return recordApi.getGuideline(token,groudId,userId, keywords, handbookIds)
    }

    override suspend fun getQuestions(
        token: String,
        groupId: Long
    ): NoteResponseBody<List<EvaluationQuestion>> {
       return recordApi.getQuestions(token,groupId)
    }

    override suspend fun postQuestions(
        token: String,
        groupId: Long,
        questions: List<Question>
    ): NoteResponseBody<Nothing> {
        return recordApi.postQuestions(token,groupId,questions)
    }

    override suspend fun modifyQuestions(
        token: String,
        groupId: Long,
        questions: List<EvaluationQuestion>
    ): NoteResponseBody<Nothing> {
        return recordApi.modifyQuestions(token,groupId,questions)
    }

    override suspend fun getAnswers(
        token: String,
        groupId: Long
    ): NoteResponseBody<List<Evaluations>> {
        return recordApi.getAnswers(token,groupId)
    }

    override suspend fun postAnswers(
        token: String,
        groupId: Long,
        questions: List<EvaluationAnswer>
    ): NoteResponseBody<Nothing> {
      return recordApi.postAnswers(token,groupId,questions)
    }

    override suspend fun modifyAnswers(
        token: String,
        groupId: Long,
        questions: List<EvaluationAnswer>
    ): NoteResponseBody<Nothing> {
        return recordApi.modifyAnswers(token,groupId,questions)
    }

    override suspend fun getStudentEvaluations(
        token: String,
        groupId: Long,
        userId: Long
    ): NoteResponseBody<List<Evaluations>> {
        return recordApi.getStudentEvaluations(token,groupId, userId)
    }


}