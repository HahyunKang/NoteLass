package com.app.note_lass.module.student.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.common.Resources
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.login.domain.repository.StudentRepository
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

}