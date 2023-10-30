package com.app.note_lass.module.group.data.groupList

data class GroupListState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val groupList : List<Group> = emptyList(),
    val isMessage : String = ""
)
