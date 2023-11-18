package com.app.note_lass.module.record.data

data class GetRecordContentState(
    var isSuccess : Boolean = false,
    var isError : Boolean = false,
    var isLoading : Boolean = false,
    var content : String = ""
)

data class PostRecordContentState(
    var isSuccess : Boolean = false,
    var isError : Boolean = false,
    var isLoading : Boolean = false,
)
