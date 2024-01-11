package com.app.note_lass.common

import com.app.note_lass.module.student.data.HandBook

data class StudentInfo(
    val userId : Int,
    val studentId : Int,
    val name : String
)
data class RequestState<T>(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val result : T? = null,
    val isError : Boolean = false
)