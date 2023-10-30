package com.app.note_lass.module.group.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.groupList.GroupListDto
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

    override suspend fun getGroupList(accessToken: String): NoteResponseBody<GroupListDto> {
        return groupApi.getGroupList(accessToken)
    }

}