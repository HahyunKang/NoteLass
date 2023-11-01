package com.app.note_lass.module.group.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.groupList.Group
import com.app.note_lass.module.group.data.groupList.GroupListDto
import com.app.note_lass.module.group.data.studentList.Student
import com.app.note_lass.module.group.domain.repository.GroupRepository
import com.app.note_lass.module.login.domain.repository.LoginRepository
import javax.inject.Inject

class GroupImpl @Inject constructor(
    private val groupApi: GroupApi
): GroupRepository {
    override suspend fun createGroup(
        accessToken: String,
        groupCreateRequest: InfoForCreate
    ): NoteResponseBody<Int> {
       return groupApi.createGroup(accessToken, groupCreateRequest)
    }

    override suspend fun getGroupList(accessToken: String): NoteResponseBody<List<Group>> {
        return groupApi.getGroupList(accessToken)
    }

    override suspend fun getStudentList(accessToken: String, id : Int): NoteResponseBody<List<Student>> {
        return groupApi.getStudentList(accessToken,id)
    }

    override suspend fun enterGroup(accessToken: String, code: Int): NoteResponseBody<String> {
        return groupApi.enterGroup(accessToken,code)
    }

}