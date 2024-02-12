package com.app.note_lass.module.group.domain.repository

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.InfoForCreate
import com.app.note_lass.module.group.data.groupList.Group
import com.app.note_lass.module.group.data.join.JoinDto
import com.app.note_lass.module.group.data.join.JoinStudentListDto
import com.app.note_lass.module.group.data.studentList.Student
import com.app.note_lass.module.upload.data.notice.Notice
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface GroupRepository {
    suspend fun createGroup(
        accessToken: String,
        groupCreateRequest: InfoForCreate
    ): NoteResponseBody<Int>

    suspend fun getGroupList(accessToken: String): NoteResponseBody<List<Group>>
    suspend fun getStudentList(accessToken: String, id: Int): NoteResponseBody<List<Student>>
    suspend fun enterGroup(accessToken: String, code: Int): NoteResponseBody<JoinDto>
    suspend fun joinGroup(accessToken: String, groupId: Long): NoteResponseBody<Nothing>
    suspend fun approveGroup(accessToken: String, groupId: Long,userId :Long): NoteResponseBody<Nothing>
    suspend fun rejectGroup(accessToken: String, groupId: Long,userId :Long): NoteResponseBody<Nothing>
    suspend fun getStudentJoinList(accessToken: String, groupId: Long) : NoteResponseBody<JoinStudentListDto>
    suspend fun createNotice(
        accessToken: String, groupId: Long,
        noticeContents: RequestBody,
        fileList: List<MultipartBody.Part?>
    ): NoteResponseBody<Nothing>
    suspend fun getNoticeList(accessToken: String, groupId: Long) : NoteResponseBody<List<Notice>>
    suspend fun getNoticeDetail(accessToken: String, noticeId: Long) : NoteResponseBody<Notice>

}