package com.app.note_lass.module.record.data

import com.app.note_lass.common.NoteResponseBody
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface RecordApi {

    @POST("api/record/excel/{groupId}/{userId}")
    suspend fun postRecord(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long,
        @Path(value = "userId")userId : Long,
        @Body recordBody : RecordBody
    ): NoteResponseBody<Nothing>


    @GET("api/record/excel/{groupId}/{userId}")
    suspend fun getRecord(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long,
        @Path(value = "userId")userId : Long,
    ): NoteResponseBody<String>

    @Multipart
    @POST("api/record/excel/{groupId}")
    suspend fun postExcel(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long,
        @Part excelFile : MultipartBody.Part
    ) : NoteResponseBody<Nothing>

    @GET("api/record/excel/{groupId}")
    suspend fun getExcel(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long
    ) : NoteResponseBody<com.app.note_lass.module.record.data.File>

    @DELETE("api/record/excel/{groupId}")
    suspend fun deleteExcel(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long
    ) : NoteResponseBody<Nothing>
    @GET("api/record/detail/{groupId}/{userId}")
    suspend fun getScore(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Long,
        @Path(value = "userId") userId : Long,
        @Query(value = "percentage") percentage: Int
    ) : NoteResponseBody<RecordScore>
    
    @GET("api/synonym")
    suspend fun getSynonym(
        @Header(value = "Authorization") accessToken : String,
        @Query(value = "word") word: String
    ) : NoteResponseBody<List<String>>

    @GET("api/guideline/{groupId}/{userId}")
    suspend fun getGuideline(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
        @Path(value = "userId") userId: Long,
        @Query(value = "keywords") keywords: String,
        @Query(value = "handbookIds") handbookIds: String,
        ) : NoteResponseBody<String>

    @POST("api/self-eval-question/{groupId}")
    suspend fun postQuestions(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
        @Body questions : List<Question>
    ) : NoteResponseBody<Nothing>

    @GET("api/self-eval-question/{groupId}")
    suspend fun getQuestions(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
    ) : NoteResponseBody<List<EvaluationQuestion>>


    @PUT("api/self-eval-question/{groupId}")
    suspend fun modifyQuestions(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
        @Body questions : List<EvaluationQuestion>
    ) : NoteResponseBody<Nothing>


    @POST("api/self-eval-answer/{groupId}")
    suspend fun postAnswers(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
        @Body questions : List<EvaluationAnswer>
    ) : NoteResponseBody<Nothing>

    @GET("api/self-eval-answer/{groupId}")
    suspend fun getAnswers(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
    ) : NoteResponseBody<List<Evaluations>>


    @PUT("api/self-eval-answer/{groupId}")
    suspend fun modifyAnswers(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
        @Body answers: List<EvaluationAnswer>
    ) : NoteResponseBody<Nothing>

    @GET("api/self-eval-answer/{groupId}/{userId}")
    suspend fun getStudentEvaluations(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
        @Path(value = "userId") userId : Long
    ) : NoteResponseBody<List<Evaluations>>
}