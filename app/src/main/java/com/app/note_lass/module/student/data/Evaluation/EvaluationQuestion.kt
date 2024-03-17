package com.app.note_lass.module.student.data.Evaluation

import androidx.compose.runtime.MutableState

data class EvaluationQuestion(
    val id : Long,
    val question : String
)

data class Question(
    val question: String
)

data class EvaluationAnswer(
    val id : Long,
    val answer : String
)

data class Evaluations(
    val questionId : Long,
    val question : String,
    val answerId : Long?,
    val answer : String?
)

data class EvaluationForSubmit(
    val questionId : Long,
    val question : String,
    val answerId : Long?,
    var answer : MutableState<String?>,
    val first : Boolean
)