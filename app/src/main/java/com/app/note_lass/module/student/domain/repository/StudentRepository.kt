package com.app.note_lass.module.login.domain.repository

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resources
import com.app.note_lass.module.student.data.HandBook
import com.app.note_lass.module.student.data.HandBookRequest
import com.app.note_lass.module.upload.data.Material.Material
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
}