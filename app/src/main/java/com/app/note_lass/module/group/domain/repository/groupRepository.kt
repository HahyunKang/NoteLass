package com.app.note_lass.module.group.domain.repository

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.GroupCreateDto
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupList.GroupListDto
import com.app.note_lass.module.login.data.LoginDto
import com.app.note_lass.module.login.data.LoginDtoTemp
import com.app.note_lass.module.login.data.LoginRequest

interface GroupRepository {
    suspend fun createGroup(accessToken : String, groupCreateRequest : InfoForCreate) : NoteResponseBody<Int>
    suspend fun getGroupList(accessToken: String) : NoteResponseBody<GroupListDto>
}