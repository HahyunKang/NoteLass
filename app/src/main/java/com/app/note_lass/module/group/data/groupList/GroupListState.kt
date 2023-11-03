package com.app.note_lass.module.group.data.groupList

import java.util.HashMap

data class GroupListState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val groupList : List<Group> = emptyList(),
    val isMessage : String = ""
)
data class GroupHashState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val groupHash : HashMap<String,Int> = hashMapOf(),
    val isMessage : String = ""
)
data class StudentHashState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val studentHash : HashMap<String,Int> = hashMapOf(),
    val isMessage : String = ""
)