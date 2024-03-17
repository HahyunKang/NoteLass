package com.app.note_lass.common

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

data class File(
    val id : Long,
    val originalFileName : String,
    val size : Int
)