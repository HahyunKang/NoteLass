package com.app.note_lass.module.login.domain.repository

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.student.data.HandBook
import com.app.note_lass.module.student.data.HandBookRequest

interface StudentRepository {
    suspend fun postHandBook(
        accessToken : String,
        groupId : Int,
        userId :Int,
        handBookRequest: HandBookRequest) : NoteResponseBody<Nothing>

    suspend fun getHanBookList(
        accessToken : String,
        groupId : Int,
        userId :Int
    ) : NoteResponseBody<List<HandBook>>

}