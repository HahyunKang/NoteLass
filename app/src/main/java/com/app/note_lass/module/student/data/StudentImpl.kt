package com.app.note_lass.module.student.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.login.domain.repository.LoginRepository
import com.app.note_lass.module.login.domain.repository.StudentRepository
import javax.inject.Inject

class StudentImpl @Inject constructor(
    private val studentApi: StudentApi
): StudentRepository {
    override suspend fun postHandBook(accessToken: String, groupId:Int,userId : Int,handBookRequest: HandBookRequest): NoteResponseBody<Nothing> {
        return studentApi.postHandBook(accessToken,groupId,userId,handBookRequest)
    }

}