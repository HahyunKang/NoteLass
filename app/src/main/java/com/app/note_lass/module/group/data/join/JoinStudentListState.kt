package com.app.note_lass.module.group.data.join

import com.app.note_lass.module.group.data.studentList.Student

data class JoinStudentListState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val joinStudentList : List<JoinStudentInfo> = emptyList(),
    val groupInfo : String ="",
    val groupCode : Int=0,
    val isMessage : String = ""
)

