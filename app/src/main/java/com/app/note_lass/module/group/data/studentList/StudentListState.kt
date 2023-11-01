package com.app.note_lass.module.group.data.studentList


data class StudentListState(
val isLoading : Boolean = false,
val isSuccess : Boolean = false,
val isError : Boolean = false,
val studentList : List<Student> = emptyList(),
val isMessage : String = ""
)
