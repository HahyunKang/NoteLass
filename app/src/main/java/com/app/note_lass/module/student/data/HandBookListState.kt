package com.app.note_lass.module.student.data

import java.util.HashMap

data class HandBookListState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val handBookList : List<HandBook> = emptyList(),
    val isError : Boolean = false
)
