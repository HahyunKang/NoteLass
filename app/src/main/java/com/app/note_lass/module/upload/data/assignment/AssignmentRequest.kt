package com.app.note_lass.module.upload.data.assignment

import java.time.LocalDateTime

data class Assignment(
    val title : String,
    val content : String,
    val deadline : LocalDateTime,
    val type : String,
    val attemptsAllowed : Int,
    val totalScore : Int
)

data class AssignmentResponse(
    val id : Long,
    val title : String,
    val content : String,
    val deadline : LocalDateTime,
    val type : String,
    val attemptsAllowed : Int,
    val totalScore : Int
)


