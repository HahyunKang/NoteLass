package com.app.note_lass.module.upload.data

data class UploadState(
    val isSuccess  : Boolean = false,
    val isLoading  : Boolean  = false,
    val isError : Boolean = false
)
