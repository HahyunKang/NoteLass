package com.app.note_lass.module.group.data

data class CreateGroupState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val code : Int = 0,
    val isMessage : String = ""
)
