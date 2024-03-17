package com.app.note_lass.module.student.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resources
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
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentApi {


    //**--------------------------생기부-----------------------------------------**//
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
    ) : NoteResponseBody<File>

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

    //**--------------------------자기평가-----------------------------------------**//

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

    //**--------------------------한줄소개-----------------------------------------**//

    @GET("api/record/introduction/{groupId}/{userId}")
    suspend fun getIntroduction(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "groupId") groupId: Long,
        @Path(value = "userId") userId : Long
    ) : NoteResponseBody<String>
    //**--------------------------학생수첩-----------------------------------------**//

    @POST("api/handbook/{groupId}/{userId}")
    suspend fun postHandBook(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Int,
        @Path(value = "userId") userId : Int,
        @Body handBookRequest: HandBookRequest
    ) : NoteResponseBody<Nothing>

    @GET("api/handbook/{groupId}/{userId}")
    suspend fun getHandBookList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Int,
        @Path(value = "userId") userId : Int,
    ) : NoteResponseBody<List<HandBook>>
    @PATCH("api/handbook/{handbookContentId}")
    suspend fun modifyHandBook(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "handbookContentId") handbookContentId: Long,
        @Body content : String
    ) : NoteResponseBody<Nothing>

    @DELETE("api/handbook/{handbookContentId}")
    suspend fun deleteHandBook(
        @Header(value = "Authorization") accessToken: String,
        @Path(value = "handbookContentId") handbookContentId: Long,
    ) : Response<Unit>

    @GET("api/notice/{groupId}")
    suspend fun getNoticeList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Int,
    ) : NoteResponseBody<List<Resources>>


    @GET("api/material/{groupId}")
    suspend fun getMaterialList(
        @Header(value = "Authorization") accessToken : String,
        @Path(value = "groupId") groupId : Int,
    ): NoteResponseBody<List<Material>>
}