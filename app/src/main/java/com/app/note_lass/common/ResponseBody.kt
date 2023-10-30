package com.app.note_lass.common

data class NoteResponseBody<T: Any?>(
    val code: Int,
    val message: String?,
    val result: T?
)