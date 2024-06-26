package com.app.note_lass.module.group.data

import com.app.note_lass.common.NoteResponseBody
import com.app.note_lass.module.group.data.groupList.Group
import com.app.note_lass.module.group.data.join.JoinDto
import com.app.note_lass.module.group.data.join.JoinStudentListDto
import com.app.note_lass.module.group.data.studentList.Student
import com.app.note_lass.module.dashboard.data.notice.Notice
import com.app.note_lass.module.group.domain.repository.GroupRepository
import com.app.note_lass.module.home.tab.notice.DashBoard
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    override suspend fun enterGroup(accessToken: String, code: Int): NoteResponseBody<JoinDto> {
        return groupApi.enterGroup(accessToken,code)
    }

    override suspend fun joinGroup(accessToken: String, groupId: Long): NoteResponseBody<Nothing> {
        return groupApi.joinGroup(accessToken, groupId)
    }

    override suspend fun approveGroup(
        accessToken: String,
        groupId: Long,
        userId: Long
    ): NoteResponseBody<Nothing> {
        return groupApi.approveGroup(accessToken,groupId,userId)
    }

    override suspend fun rejectGroup(
        accessToken: String,
        groupId: Long,
        userId: Long
    ): NoteResponseBody<Nothing> {
        return groupApi.rejectGroup(accessToken,groupId,userId)
    }

    override suspend fun getStudentJoinList(
        accessToken: String,
        groupId: Long
    ): NoteResponseBody<JoinStudentListDto> {
        return  groupApi.getStudentJoinList(accessToken,groupId)
    }

//    override suspend fun createNotice(
//        accessToken: String,
//        groupId: Long,
//        noticeContents: RequestBody,
//        fileList: List<MultipartBody.Part?>
//    ): NoteResponseBody<Nothing> {
//        return groupApi.createNotice(accessToken,groupId,noticeContents,fileList)
//    }

//    override suspend fun modifyNotice(
//        accessToken: String,
//        groupId: Long,
//        noticeId: Long,
//        noticeContents: RequestBody,
//        fileList: List<MultipartBody.Part?>
//    ): NoteResponseBody<Nothing> {
//        return groupApi.modifyNotice(accessToken, groupId, noticeId, noticeContents, fileList)
//    }
//
//    override suspend fun getNoticeList(
//        accessToken: String,
//        groupId: Long
//    ): NoteResponseBody<List<Notice>> {
//        return groupApi.getNoticeList(accessToken, groupId)
//    }
//
//    override suspend fun getNoticeDetail(
//        accessToken: String,
//        noticeId: Long
//    ): NoteResponseBody<Notice> {
//        return groupApi.getNoticeDetail(accessToken, noticeId)
  //  }

    override suspend fun approveAllGroup(
        accessToken: String,
        groupId: Long
    ): NoteResponseBody<Nothing> {
        return groupApi.approveAllGroup(accessToken, groupId)
    }

    override suspend fun deleteGroup(
        accessToken: String,
        groupId: Long
    ): NoteResponseBody<Nothing> {
        return groupApi.deleteGroup(accessToken, groupId)
    }

    override suspend fun deleteStudent(
        accessToken: String,
        groupId: Long,
        userId: Long
    ): NoteResponseBody<Nothing> {
        return groupApi.deleteStudent(accessToken, groupId, userId)
    }

//    override suspend fun getDashboardsInHome(accessToken: String): NoteResponseBody<List<DashBoard>> {
//        return groupApi.getDashboardsInHome(accessToken)
//    }
//
//    override suspend fun getDashboardsInGroup(
//        accessToken: String,
//        groupId: Long
//    ): NoteResponseBody<List<DashBoard>> {
//        return groupApi.getDashboardsInGroup(accessToken, groupId)
//    }

}