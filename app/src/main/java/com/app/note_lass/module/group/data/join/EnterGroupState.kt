package com.app.note_lass.module.group.data.join
data class EnterGroupState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val groupInfo : String = "",
    val groupId : Long = 0,
    val isErrorCode : Boolean = false,
    val isMessage : String = ""
)

data class JoinGroupState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isDuplicate : Boolean = false,
    val isMessage : String = ""
)

data class ApproveOrRejectGroupState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isError : Boolean = false,
    val isApprove : Boolean = false,
    val isMessage : String = ""
)

